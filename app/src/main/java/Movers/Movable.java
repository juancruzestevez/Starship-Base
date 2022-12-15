package Movers;

import Entities.Entity;

public interface Movable {

    double getXs();
    double getYs();
    Point getPosition();
    double calculateSpeed();

    String getId();

    Entity getEntity();
    double getRotation();


}
