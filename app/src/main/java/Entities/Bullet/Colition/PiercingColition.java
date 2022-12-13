package Entities.Bullet.Colition;

import Entities.Bullet.Bullet;
import Entities.Entity;
import Entities.EntityType;

import java.util.Optional;

public class PiercingColition implements BulletColition{
    @Override
    public Optional<Entity> collide(Bullet bullet, Entity entity) {
        return Optional.of(bullet);
    }
}
