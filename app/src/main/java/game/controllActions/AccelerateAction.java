package game.controllActions;

import Movers.MoverShip;
import game.ShipController;

public class AccelerateAction implements Action<ShipController>{

    public AccelerateAction() {
    }

    @Override
    public ShipController action(ShipController shipController) {
        return new ShipController(new MoverShip(
                shipController.getShipMover().getShip(),
                shipController.getShipMover().getXs() + shipController.getShipMover().getAcceleration()*shipController.getShipMover().getX(),
                shipController.getShipMover().getYs() + shipController.getShipMover().getAcceleration()*shipController.getShipMover().getY(),
                shipController.getShipMover().getRotation(), shipController.getPosition()), shipController.getShipWeapon(), shipController.getShipActions(), shipController.getEntityAction(), shipController.getUpgrades());

    }
}
