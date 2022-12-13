package Entities;
import Entities.Bullet.Bullet;

import java.util.Optional;
public class PowerUp implements Entity{

    final EntityType entityType = EntityType.POWERUP;
    final String id;
    final double size;

    public PowerUp(String id, double size) {
        this.id = id;
        this.size = size;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public EntityType getType() {
        return entityType;
    }

    @Override
    public double getDamage() {
        return 0;
    }

    @Override
    public Optional<Entity> collide(Entity other) {
        if (other.getType() == EntityType.STARSHIP){
            return Optional.empty();
        }else{
            return Optional.of(this);
        }
    }

    @Override
    public double getsize() {
        return size;
    }
}
