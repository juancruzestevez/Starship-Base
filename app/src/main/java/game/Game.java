package game;
import Entities.Entity;
import Movers.Movable;
import Movers.Mover;
import java.util.List;
import java.util.Map;
public interface Game {
    Game handleShipAction(String id, KeyMovement keyMovement);
    Game moveEntities();
    Game collideEntity(String id1, String id2);
    Map<String,Double> getPoints();
    Integer getNumberOfShips();
    Game pause();
    Game resume();
    List<ShipController> getShips();
    List<Mover> getEntities();
    Map<String, Mover> removeEntity(Map<String, Mover> entity);
    List<String> filterEntities();
    List<String> getIdsToRemove();
    Game generateEntity(Entity entity);
    Map<String, ShipController> removeControllers(Map<String, ShipController> controllers);
    String getMaxPoints();
}