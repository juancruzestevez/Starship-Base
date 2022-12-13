package Entities;

import constant.Constants;
import Generators.IdGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Asteroid implements Entity{
    final EntityType entityType = EntityType.ASTEROID;
    final String id;
    final double size;

    public Asteroid( String id, double size) {
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
}
