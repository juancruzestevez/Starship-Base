package game;

import Entities.Entity;
import Movers.Movable;
import Movers.Mover;

import java.util.List;
import java.util.Map;

public class PausedGame implements Game{
    private final GameState gameState;

    public PausedGame(GameState gameState) {
        this.gameState = gameState;
    }


    @Override
    public Game handleShipAction(String id, KeyMovement keyMovement) {
        return this;
    }

    @Override
    public Game moveEntities() {
        return this;
    }

    @Override
    public Game collideEntity(String id1, String id2) {
        return this;
    }

    @Override
    public Map<String, Double> getPoints() {
        return gameState.getPoints();
    }

    @Override
    public Integer getNumberOfShips() {
        return gameState.getNumberOfShips();
    }

    @Override
    public Game pause() {
        return this;
    }

    @Override
    public Game resume() {
        return gameState;
    }

    @Override
    public List<ShipController> getShips() {
        return gameState.getShips();
    }

    @Override
    public List<Mover> getEntities() {
        return gameState.getEntities();
    }

    @Override
    public Map<String, Mover> removeEntity(Map<String, Mover> entity) {
        return gameState.removeEntity(entity);
    }

    @Override
    public List<String> filterEntities() {
        return gameState.filterEntities();
    }
    public List<String> getIdsToRemove(){
        return gameState.getIdsToRemove();
    }

    @Override
    public Game generateEntity(Entity entity) {
        return this;
    }


    @Override
    public Map<String, ShipController> removeControllers(Map<String, ShipController> controllers) {
        return Map.of();
    }

}
