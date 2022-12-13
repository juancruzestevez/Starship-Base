package game;
import Entities.Entity;
import Entities.weapon.Weapon;
import Movers.Movable;
import Movers.Mover;
import Movers.MoverShip;
import Movers.Point;
import game.controllActions.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ShipController implements Movable {
    public final MoverShip shipController;
    public final Weapon shipWeapon;
    public final Map<KeyMovement, Action> shipAactions;
    public final Map<KeyMovement, Action> entityAction;
    public final List<Weapon> upgrades;

    public ShipController(MoverShip shipController, Weapon shipWeapon, Map<KeyMovement, Action> shipAactions, Map<KeyMovement, Action> entityAction, List<Weapon> upgrades) {
        this.shipController = shipController;
        this.shipWeapon = shipWeapon;
        this.shipAactions = shipAactions;
        this.entityAction = entityAction;
        this.upgrades = upgrades;
    }

    public String getId(){
        return shipController.getShip().getId();
    }

    @Override
    public Entity getEntity() {
        return shipController.getEntity();
    }

    @Override
    public double getRotation() {
        return shipController.getRotation();
    }

    public ShipController update(){
        return new ShipController(shipController.update(), shipWeapon, shipAactions, entityAction, upgrades);
    }

    public List<Mover> shoot(){
        return shipWeapon.shoot(shipController.position, shipController.getId(), shipController.getRotation());
    }

    @Override
    public double getXs() {
        return shipController.getXs();
    }

    @Override
    public double getYs() {
        return shipController.getYs();
    }

    @Override
    public Point getPosition() {
        return shipController.getPosition();
    }

    @Override
    public double calculateSpeed() {
        return shipController.calculateSpeed();
    }

    public MoverShip getShipMover(){
        return shipController;
    }

    public Weapon getShipWeapon() {
        return shipWeapon;
    }

    public Map<KeyMovement, Action> getShipActions() {
        return shipAactions;
    }

    public Map<KeyMovement, Action> getEntityAction() {
        return entityAction;
    }

    public List<Weapon> getUpgrades() {
        return upgrades;
    }

    public ShipController shipAction(KeyMovement keyMovement){
        if (shipAactions.containsKey(keyMovement)){
            return (ShipController) shipAactions.get(keyMovement).action(this);
        }else return this;
    }

    public List<Mover> shootAction(KeyMovement keyMovement){
        if (entityAction.containsKey(keyMovement)){
            return (List<Mover>) entityAction.get(keyMovement).action(this);
        }else return new ArrayList<>();
    }


}