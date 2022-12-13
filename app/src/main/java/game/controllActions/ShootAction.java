package game.controllActions;

import Movers.Mover;
import game.ShipController;

import java.util.List;

public class ShootAction implements Action<List<Mover>>{
    @Override
    public List<Mover> action(ShipController shipController) {
        return shipController.getShipWeapon().shoot(shipController.getShipMover().getPosition(), shipController.getId(), shipController.getShipMover().getRotation());
    }
}
