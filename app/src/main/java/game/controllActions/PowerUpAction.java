package game.controllActions;

import Entities.Ship;
import Movers.MoverShip;
import game.ShipController;

public class PowerUpAction implements Action{

    public PowerUpAction(){
    }

    @Override
    public Object action(ShipController shipController) {
        int power = shipController.getShipMover().getShip().getPower();
        if (power >= shipController.getUpgrades().size()){
            power -= shipController.getUpgrades().size();
        }
        Ship ship = shipController.getShipMover().getShip();
        return new ShipController(new MoverShip(
                new Ship(ship.getId(), ship.getLife(), ship.getsize(), 0),
                shipController.getShipMover().getXs(), shipController.getShipMover().getYs(),
                shipController.getShipMover().getRotation(), shipController.getPosition()),
                shipController.getUpgrades().get(power),
                shipController.getShipActions(), shipController.getEntityAction(), shipController.getUpgrades());
    }

}
