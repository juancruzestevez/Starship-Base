package Entities.Bullet.Colition;

import Entities.Bullet.Bullet;
import Entities.Collideable;
import Entities.Entity;

import java.util.List;
import java.util.Optional;

public interface BulletColition{

    public Optional<Entity> collide(Bullet bullet, Entity entity);
}
