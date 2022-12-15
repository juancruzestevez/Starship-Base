package Entities.Bullet.Colition;

import Entities.Bullet.Bullet;
import Entities.Entity;

import java.util.Optional;

public interface BulletColition{

    public Optional<Entity> collide(Bullet bullet, Entity entity);
}
