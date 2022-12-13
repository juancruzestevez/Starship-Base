package adapters;

import static Generators.RandomNumberGenerator.getRandomNumber;
import static constant.Constants.*;

import Entities.Asteroid;
import Generators.IdGenerator;
import Generators.Side;
import Movers.Mover;
import Movers.Point;
import edu.austral.ingsis.starships.ui.ElementColliderType;
import edu.austral.ingsis.starships.ui.ElementModel;
import edu.austral.ingsis.starships.ui.ImageRef;
import game.ShipController;


public class Parser {
    public static ElementModel parsController(ShipController shipController){
        return new ElementModel(shipController.getId(), shipController.getShipMover().getPosition().getX(), shipController.getShipMover().getPosition().getY(), shipController.getShipMover().getEntity().getsize(), shipController.getShipMover().getEntity().getsize(), shipController.getShipMover().getRotation(), ElementColliderType.Triangular, new ImageRef("starship", SHIP_HEIGHT, SHIP_WIDTH));
    }

    public static ElementModel parsEntity(Mover mover){
        return switch (mover.getEntity().getType()) {
            case BULLET -> new ElementModel(mover.getId(), mover.getPosition().getX(), mover.getPosition().getY(), 10, 10, mover.calculateDegree(), ElementColliderType.Rectangular, null);
            case ASTEROID -> new ElementModel(mover.getId(), mover.getPosition().getX(), mover.getPosition().getY(), mover.getEntity().getsize(), mover.getEntity().getsize(), mover.calculateDegree(), ElementColliderType.Elliptical, null);
            case POWERUP -> new ElementModel(mover.getId(), mover.getPosition().getX(), mover.getPosition().getY(), mover.getEntity().getsize(), mover.getEntity().getsize(), mover.calculateDegree(), ElementColliderType.Elliptical, null);
            default -> null;
        };

    }
}
