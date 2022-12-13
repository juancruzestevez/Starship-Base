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
import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.stage.Stage


private var adapter = Adapter(createGameState(), SPAWN_PROBABILITY)
private val imageResolver = CachedImageResolver(DefaultImageResolver())
private val facade = ElementsViewFacade(imageResolver)
private val keyTracker = KeyTracker()


fun main() {
    launch(MyStarships::class.java)
}

class MyStarships() : Application() {
    companion object {
        val STARSHIP_IMAGE_REF = ImageRef("starship", 70.0, 70.0)
    }

    override fun start(primaryStage: Stage) {

        adapter.parseEntities(facade.elements)

        facade.timeListenable.addEventListener(MyTimeListener(facade.elements))
        facade.collisionsListenable.addEventListener(MyCollisionListener())
        keyTracker.keyPressedListenable.addEventListener(MyKeyPressedListener())
        val scene = Scene(facade.view)
        keyTracker.scene = scene

        primaryStage.scene = scene
        primaryStage.height = 1000.0
        primaryStage.width = 1000.0

        facade.start()
        keyTracker.start()
        primaryStage.show()
    }

    override fun stop() {
        facade.stop()
        keyTracker.stop()
    }
}

class MyTimeListener(private val elements: Map<String, ElementModel>) : EventListener<TimePassed> {
    override fun handle(event: TimePassed) {
        adapter = adapter.moveEntities()
        updateFacadeControllers()
        updateFacadeEntities()
        removeEntities()
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
        adapter.gamestate.ships.forEach { controller ->
            keyBindMap[controller.id]?.forEach { (movement, keyCode) ->
                if (key == keyCode) {
                    adapter = adapter.handleShipAction(controller.id, movement)
                }
            }
        }
    }
/*    override fun handle(event: KeyPressed) {
        when(event.key) {
            KeyCode.UP -> adapter = adapter.handleShipAction("STARSHIP-0", KeyMovement.ACCELERATE)
            KeyCode.DOWN -> adapter = adapter.handleShipAction("STARSHIP-0", KeyMovement.SHOOT)
            KeyCode.LEFT -> adapter = adapter.handleShipAction("STARSHIP-0", KeyMovement.TURN_LEFT)
            KeyCode.RIGHT -> adapter = adapter.handleShipAction("STARSHIP-0", KeyMovement.TURN_RIGHT)
            KeyCode.SPACE -> adapter = adapter.handleShipAction("STARSHIP-0", KeyMovement.POWERUP)
            KeyCode.P -> adapter = adapter.pause()
            KeyCode.R -> adapter.resume()

            else -> {}
        }
    }
*/
}