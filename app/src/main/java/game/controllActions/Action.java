package game.controllActions;

import game.ShipController;

public interface Action<T> {
    public T action(ShipController shipController);
}
