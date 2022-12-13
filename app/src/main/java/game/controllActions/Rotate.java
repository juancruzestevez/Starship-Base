package game.controllActions;

import Movers.MoverShip;
import game.ShipController;

public class Rotate implements Action<ShipController>{
    private final double rotation;

    public Rotate(double rotation) {
        this.rotation = rotation;
    }

    @Override
    public ShipController action(ShipController shipController) {
        return new ShipController(new MoverShip(shipController.getShipMover().getShip(),
                shipController.getShipMover().getXs(), shipController.getShipMover().getYs(), shipController.getShipMover().getRotation() + rotation, shipController.getPosition()), shipController.getShipWeapon(),  shipController.getShipActions(), shipController.getEntityAction(), shipController.getUpgrades());
    }
}
