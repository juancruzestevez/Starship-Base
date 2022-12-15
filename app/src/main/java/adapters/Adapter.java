package adapters;

import Entities.Asteroid;
import Generators.EntityFactory;
import edu.austral.ingsis.starships.ui.ElementModel;
import game.*;
import javafx.collections.ObservableMap;

public class Adapter{

    private final Game gamestate;

    private final double spawnProbs;

    public Adapter(Game gamestate, double spawnProbs) {
        this.gamestate = gamestate;
        this.spawnProbs = spawnProbs;
    }

    public Adapter handleShipAction(String id, KeyMovement keyMovement) {
        return new Adapter(gamestate.handleShipAction(id, keyMovement), spawnProbs);
    }

    public Adapter moveEntities() {
        if (Math.random() < spawnProbs){
            return new Adapter(gamestate.generateEntity(EntityFactory.createAsteroid()).moveEntities(), spawnProbs);
        }
        if (Math.random() < spawnProbs){
            return new Adapter(gamestate.generateEntity(EntityFactory.createPowerUp()).moveEntities(), spawnProbs);
        }
        return new Adapter(gamestate.moveEntities(), spawnProbs);
    }

    public Adapter collideEntity(String id1, String id2) {
        return new Adapter(gamestate.collideEntity(id1, id2), spawnProbs);
    }

    public Adapter pause() {
        return new Adapter(gamestate.pause(), spawnProbs);
    }

    public Adapter resume() {
        return new Adapter(gamestate.resume(), spawnProbs);
    }

    public void parseEntities(ObservableMap<String, ElementModel> elements){
        for (ShipController shipController : gamestate.getShips()){
            elements.put(shipController.getId(), Parser.parsController(shipController));
        }
    }

    public Game getGamestate() {
        return gamestate;
    }

}
