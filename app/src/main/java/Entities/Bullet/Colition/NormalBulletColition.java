package Entities.Bullet.Colition;

import Entities.Bullet.Bullet;
import Entities.Entity;

import java.util.Objects;
import java.util.Optional;

public class NormalBulletColition implements BulletColition{
    @Override
    public Optional<Entity> collide(Bullet bullet, Entity entity) {
        if (Objects.equals(entity.getId(), bullet.getOwnerId())){
            return Optional.of(bullet);
        }else{
            return Optional.empty();
        }
    }
}
