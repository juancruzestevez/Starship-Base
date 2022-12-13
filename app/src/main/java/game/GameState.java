package game;

import Entities.*;
import Generators.EntityFactory;
import Movers.Movable;
import Movers.Mover;
import Movers.MoverShip;

import java.util.*;

public class GameState implements Game{
    private final double width;
    private final double height;
    private final Map<String, ShipController> controllers;
    private final Map<String, Mover> entities;
    private final List<String> idsToRemove;
    private final Map<String, Integer> points;

    public GameState(double width, double height, Map<String, ShipController> controllers, Map<String, Mover> entities, List<String> idsToRemove, Map<String, Integer> points) {
        this.width = width;
        this.height = height;
        this.controllers = controllers;
        this.entities = entities;
        this.idsToRemove = idsToRemove;
        this.points = points;
    }

    @Override
    public Game handleShipAction(String id, KeyMovement keyMovement) {
        ShipController controller = findControllerById(id);
        Map<String, ShipController> newControllers = new HashMap<>(controllers);
        Map<String, Mover> newBullets = new HashMap<>(entities);
        if (controller.shipAactions.containsKey(keyMovement)){
            newControllers.put(id, controller.shipAction(keyMovement));
        }
        if (controller.entityAction.containsKey(keyMovement)){
            addBulletsEntity(controller.shootAction(keyMovement), newBullets);
        }
        return new GameState(width, height, newControllers, newBullets, idsToRemove, points);
    }

    private void addBulletsEntity(List<Mover> bullets, Map<String, Mover> newEntities){
        for (Mover mover : bullets) {
            newEntities.put(mover.getId(), mover);
        }
    }

    private ShipController findControllerById(String id){
        return controllers.getOrDefault(id, null);
    }

    private Movable findMovable(String id){
        if (controllers.containsKey(id)){
            return controllers.get(id);
        }if (entities.containsKey(id)){
            return entities.get(id);
        }
        return null;
    }

    @Override
    public Game moveEntities() {
        Map<String, Mover> updatedEntities = new HashMap<>();
        Map<String, ShipController> updatedControllers = new HashMap<>();
        for(String entity : removeEntity(entities).keySet()){
            updatedEntities.put(entity, entities.get(entity).update());
        }
        for(String ships : removeControllers(controllers).keySet()){
            updatedControllers.put(ships, controllers.get(ships).update());
        }
        return new GameState(width, height, updatedControllers, updatedEntities, filterEntities(), points);
    }

    @Override
    public Game collideEntity(String id1, String id2) {
        Map<String, Mover> newEntities = new HashMap<>(entities);
        Map<String, ShipController> newControllers = new HashMap<>(controllers);
        List<String> newIdsToRemove = new ArrayList<>(idsToRemove);

        Movable entity1 = findMovable(id1);
        Movable entity2 = findMovable(id2);

        Optional<Entity> newEntity1 = entity1.getEntity().collide(entity2.getEntity());
        Optional<Entity> newEntity2 = entity2.getEntity().collide(entity1.getEntity());

        addNewEntity(newEntities, newControllers, newIdsToRemove, entity1, newEntity1);
        addNewEntity(newEntities, newControllers, newIdsToRemove, entity2, newEntity2);

        return new GameState(width, height, newControllers, newEntities, newIdsToRemove, points);
    }

    private void addNewEntity(Map<String, Mover> newEntities, Map<String, ShipController> newControllers, List<String> newIdsToRemove, Movable entity1, Optional<Entity> newEntity1) {
        if (newEntity1.isEmpty()) {
            newIdsToRemove.add(entity1.getId());
        }
        else {
            if (entity1.getEntity().getType().equals(EntityType.STARSHIP)) {
                ShipController newSipController = controllers.get(entity1.getId());
                newControllers.put(entity1.getId(), new ShipController(new MoverShip((Ship) newEntity1.get(), entity1.getXs(), entity1.getYs(), entity1.getRotation(), entity1.getPosition()), newSipController.getShipWeapon(), newSipController.getShipActions(), newSipController.entityAction, newSipController.getUpgrades()));
            }
            else {
                newEntities.put(entity1.getId(), new Mover(newEntity1.get(), entity1.getPosition(), entity1.getXs(), entity1.getYs()));
            }
        }
    }

    @Override
    public Map<String, Integer> getPoints() {
        return points;
    }

    @Override
    public Integer getNumberOfShips() {
        return controllers.size();
    }

    @Override
    public Game pause() {
        return new PausedGame(this);
    }

    @Override
    public Game resume() {
        return this;
    }

    @Override
    public List<ShipController> getShips() {
        return controllers.values().stream().toList();
    }

    @Override
    public List<Mover> getEntities() {
        return entities.values().stream().toList();
    }

    public List<String> filterEntities(){
        List<String> toRemove = new ArrayList<>();
        for (Mover bullets : entities.values().stream().toList()){
            if (bullets.getPosition().getX() > width || bullets.getPosition().getY() > height || bullets.getPosition().getX() < 0 || bullets.getPosition().getY() < 0){
                toRemove.add(bullets.getId());
            }
        }
        return toRemove;
    }

    public Map<String, Mover> removeEntity(Map<String, Mover> entity){
        HashMap<String, Mover> newEntities= new HashMap<>(entity);
        for (String ids : idsToRemove){
            newEntities.remove(ids);
        }
        return newEntities;
    }

    public Map<String, ShipController> removeControllers(Map<String, ShipController> controllers){
        HashMap<String, ShipController> newControllers= new HashMap<>(controllers);
        for (String ids : idsToRemove){
            newControllers.remove(ids);
        }if (getNumberOfShips() == 0){
            System.exit(0);
        }
        return newControllers;
    }

    public List<String> getIdsToRemove(){
        return idsToRemove;
    }

    public Game generateAsteroid(){
        Map<String, Mover> newEntities = new HashMap<>(entities);
        Mover newAsteroid = EntityFactory.spawnAsteroid(EntityFactory.generateRandomPoint());
        newEntities.put(newAsteroid.getId(), newAsteroid);
        return new GameState(width, height, controllers, newEntities, filterEntities(), points);
    }

    public Game generatePowerUp(){
        Map<String, Mover> newEntities = new HashMap<>(entities);
        Mover newPowerUp = EntityFactory.spawnPowerUp(EntityFactory.generateRandomPoint());
        newEntities.put(newPowerUp.getId(), newPowerUp);
        return new GameState(width, height, controllers, newEntities, filterEntities(), points);
    }
}