package Entities;

import Movers.Movable;
import Movers.Mover;
import Movers.Point;

import java.util.Optional;

public interface Collideable<T> extends Entity{
    Optional<T> collide(Collideable other);
}
