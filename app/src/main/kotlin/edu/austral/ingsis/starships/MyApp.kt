package edu.austral.ingsis.starships

import constant.Constants.*
import Generators.EntityFactory.*
import adapters.Adapter
import adapters.Parser
import config.MyFileReader
import constant.Constants
import edu.austral.ingsis.starships.ui.*
import edu.austral.ingsis.starships.ui.ElementColliderType.*
import game.KeyMovement
import javafx.application.Application
import javafx.application.Application.launch
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.scene.paint.Color
import javafx.stage.Stage


private var adapter = Adapter(createGameState(2), SPAWN_PROBABILITY)
private val imageResolver = CachedImageResolver(DefaultImageResolver())
private val facade = ElementsViewFacade(imageResolver)
private val keyTracker = KeyTracker()

fun main() {
    launch(MyStarships::class.java)
}

class MyStarships() : Application() {
    var gameScene = Scene(StackPane())

    override fun start(primaryStage: Stage) {

        adapter.parseEntities(facade.elements)
        facade.collisionsListenable.addEventListener(MyCollisionListener())
        keyTracker.keyPressedListenable.addEventListener(MyKeyPressedListener())
        facade.view.id = "game"
        val (generalPane, lifeLabels, scores) = buildGeneralPane()
        facade.timeListenable.addEventListener(MyTimeListener(facade.elements, lifeLabels, scores))
        val scene = Scene(generalPane)
        //val scene = Scene(facade.view)
        keyTracker.scene = scene
        scene.stylesheets.add(this::class.java.classLoader.getResource("Style.css")?.toString())

        primaryStage.scene = scene
        primaryStage.height = GAME_HEIGHT
        primaryStage.width = GAME_WIDTH
        facade.showCollider.value = false
        facade.start()
        keyTracker.start()
        primaryStage.show()


    }

    private fun buildGeneralPane(): Triple<StackPane, List<Label>, List<Label>> {
        val generalPane = StackPane()

        val (livesLayout, lifeLabels) = buildLivesLayout()
        val (scoreLayout, scoreLabels) = buildScoresLayout()

        generalPane.children.addAll(facade.view, livesLayout, scoreLayout)
        return Triple(generalPane, lifeLabels, scoreLabels)

    }

    private fun buildLivesLayout(): Pair<VBox, List<Label>> {
        return buildVerticalPlayerDataLabelLayout(Pos.TOP_LEFT, "HEALTH")
    }
    private fun buildScoresLayout(): Pair<VBox, List<Label>> {
        return buildVerticalPlayerDataLabelLayout(Pos.TOP_RIGHT, "SCORES")
    }

    private fun buildVerticalPlayerDataLabelLayout(position: Pos, layoutTitle: String): Pair<VBox, List<Label>> {
        val verticalLayout = VBox()
        verticalLayout.alignment = position
        val playerQuantity = adapter.gamestate.numberOfShips
        val labelList = ArrayList<Label>();
        labelList.add(generateLayoutTitle(layoutTitle))
        repeat(playerQuantity) {index ->
            labelList.add(generateInitialPlayerLabel(index))
        }
        verticalLayout.children.addAll(labelList)
        return verticalLayout to labelList
    }

    private fun generateInitialPlayerLabel(index: Int): Label {
        val label = Label("")
        label.textFill = Color.WHITE
        return label

    }
    private fun generateLayoutTitle(title: String): Label {
        val layoutTitle = Label(title)
        layoutTitle.textFill = Color.WHITE
        return layoutTitle
    }

    override fun stop() {
        facade.stop()
        keyTracker.stop()
    }
}

class MyTimeListener(private val elements: Map<String, ElementModel>, private var lifeLabels: List<Label>, private var scoreLabels: List<Label>) : EventListener<TimePassed> {
    override fun handle(event: TimePassed) {
        adapter = adapter.moveEntities()
        updateFacadeControllers()
        updateFacadeEntities()
        removeEntities()
        updateLabels()
    }

    private fun removeEntities(){
        adapter.gamestate.idsToRemove.forEach{
            facade.elements.remove(it)
        }
    }

    private fun updateFacadeControllers() {
        adapter.gamestate.ships.forEach{
            if (facade.elements.containsKey(it.id)) {
                facade.elements[it.id]?.x?.set(it.shipController.getPosition().getX())
                facade.elements[it.id]?.y?.set(it.shipController.getPosition().getY())
                facade.elements[it.id]?.rotationInDegrees?.set(it.shipController.getRotation()+270)
            } else {
                facade.elements[it.id] = Parser.parsController(it)
            }
        }
    }
    private fun updateFacadeEntities(){
        adapter.gamestate.entities.forEach{
            if (facade.elements.containsKey(it.id)) {
                facade.elements[it.id]?.x?.set(it.getPosition().getX())
                facade.elements[it.id]?.y?.set(it.getPosition().getY())
                facade.elements[it.id]?.height?.set(it.entity.getsize())
                facade.elements[it.id]?.width?.set(it.entity.getsize())
                facade.elements[it.id]?.rotationInDegrees?.set(it.calculateDegree())
            } else {
                facade.elements[it.id] = Parser.parsEntity(it)
            }
        }
    }

    private fun updateLabels(){
        updateScoreLabels()
        updateHealthLabels()
    }

    private fun updateHealthLabels() {
        var numberShip = 0;
        adapter.gamestate.ships.forEach {
            val shipNumber = it.id
            lifeLabels.get(numberShip).text = "Player ${(shipNumber)}: " + it.shipMover.ship.life + ", power: " + it.shipMover.ship.power
            numberShip += 1
        }
    }

    private fun updateScoreLabels() {
        var numberShip = 0;
        adapter.gamestate.points.forEach {
            val shipNumber = it.key
            scoreLabels.get(numberShip).text = "Player ${(shipNumber)}: " + it.value
            numberShip += 1
        }
    }

}

class MyCollisionListener() : EventListener<Collision> {
    override fun handle(event: Collision) {
        println("${event.element1Id} ${event.element2Id}")
        adapter =adapter.collideEntity(event.element1Id, event.element2Id)
        removeEntities()
    }

    private fun removeEntities(){
        adapter.gamestate.idsToRemove.forEach{
            facade.elements.remove(it)
        }
    }


}

class MyKeyPressedListener(): EventListener<KeyPressed> {

    private var keyBindMap: Map<String, Map<KeyMovement, KeyCode>> = MyFileReader(KEYS_PATH).readBindings()
    override fun handle(event: KeyPressed) {
        val key = event.key
        handlePauseResume(key)
        adapter.gamestate.ships.forEach { controller ->
            keyBindMap[controller.id]?.forEach { (movement, keyCode) ->
                if (key == keyCode) {
                    adapter = adapter.handleShipAction(controller.id, movement)
                }
            }
        }
    }

    private fun handlePauseResume(key: KeyCode) {
        when (key) {
            keyCodeOf(PAUSE_GAME) -> adapter = adapter.pause()
            keyCodeOf(RESUME_GAME) -> adapter = adapter.resume()
            else -> {}
        }
    }

    private fun keyCodeOf(string: String): Any {
        return KeyCode.valueOf(string)
    }
}