package Entities.weapon;
import Entities.Bullet.BulletType;
import Movers.Mover;
import Entities.weapon.Shoot.Shoot;
import Movers.Point;

import java.util.List;

public class Weapon {
    final double speed;

    final BulletType bulletType;

    final Shoot shooter;

    public Weapon(double speed, BulletType bulletType, Shoot shooter) {
        this.speed = speed;
        this.bulletType = bulletType;
        this.shooter = shooter;
    }

    public List<Mover> shoot(Point point, String ownerId, double degree){
        return shooter.shoot(bulletType, point, speed, ownerId, degree);
    }
}
