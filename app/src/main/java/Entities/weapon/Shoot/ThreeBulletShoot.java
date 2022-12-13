package Entities.weapon.Shoot;

import Entities.Bullet.BulletType;
import Generators.EntityFactory;
import Movers.Mover;
import Movers.Point;

import java.util.ArrayList;
import java.util.List;

public class ThreeBulletShoot implements Shoot{
    @Override
    public List<Mover> shoot(BulletType bulletType, Point point, double speed, String ownerId, double degree) {
        List<Mover> bullets = new ArrayList<>();
        bullets.add(EntityFactory.createBullet(bulletType,ownerId, degree, point));
        bullets.add(EntityFactory.createBullet(bulletType,ownerId, degree + 30, point));
        bullets.add(EntityFactory.createBullet(bulletType,ownerId, degree - 30, point));
        return bullets;
    }
}
