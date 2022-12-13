package game.controllActions;

import Entities.Ship;
import Movers.MoverShip;
import game.ShipController;

public class PowerUpAction implements Action{

    public PowerUpAction(){
    }

    @Override
    public Object action(ShipController shipController) {
        Ship ship = shipController.getShipMover().getShip();
        return new ShipController(new MoverShip(
                new Ship(ship.getId(), ship.getLife(), ship.getsize(), ship.getLives(), 0),
                shipController.getShipMover().getXs() + shipController.getShipMover().getAcceleration()*shipController.getShipMover().getX(),
                shipController.getShipMover().getYs() + shipController.getShipMover().getAcceleration()*shipController.getShipMover().getY(),
                shipController.getShipMover().getRotation(), shipController.getPosition()), shipController.getUpgrades().get(shipController.getShipMover().getShip().getPower()), shipController.getShipActions(), shipController.getEntityAction(), shipController.getUpgrades());
    }
}
