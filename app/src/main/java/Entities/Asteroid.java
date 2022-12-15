package Entities;

import constant.Constants;

import java.util.Optional;

public class Asteroid implements Entity{
    final EntityType entityType = EntityType.ASTEROID;
    final String id;
    final double size;

    final double damage;

    public Asteroid(String id, double size) {
        this.id = id;
        this.size = size;
        this.damage = size * Constants.ASTEROID_DAMAGE;
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
        return damage;
    }

    @Override
    public Optional<Entity> collide(Entity other) {
        if (other.getType() == EntityType.BULLET){
            if (size <= Constants.MIN_ASTEROID_SIZE){
                return Optional.empty();
            }else{
                return Optional.of(new Asteroid(id, size - other.getDamage()));
            }
        }else{
            return Optional.of(this);
        }
    }

    @Override
    public double getsize() {
        return size;
    }

    @Override
    public String getOwnerId() {
        return id;
    }
}
