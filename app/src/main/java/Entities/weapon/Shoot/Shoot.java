package Entities.weapon.Shoot;

import Entities.Bullet.BulletType;
import Movers.Mover;
import Movers.Point;

import java.util.List;

public interface Shoot {
    public List<Mover> shoot(BulletType bulletType, Point point, double speed, String ownerId, double degree);
}
