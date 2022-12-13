package Entities.Bullet.Colition;

import Entities.Bullet.Bullet;
import Entities.Collideable;
import Entities.Entity;
import Entities.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NormalBulletColition implements BulletColition{
    @Override
    public Optional<Entity> collide(Bullet bullet, Entity entity) {
        if (entity.getType() == EntityType.ASTEROID){
            return Optional.empty();
        }else{
            return Optional.of(bullet);
        }
    }
}
