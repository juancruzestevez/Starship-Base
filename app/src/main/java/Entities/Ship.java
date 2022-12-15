package Entities;

import constant.Constants;

import java.util.Objects;
import java.util.Optional;

public final class Ship implements Entity{
    final String id;
    final EntityType entityType = EntityType.STARSHIP;
    final double life;
    final double size;
    final double damage = Constants.LIFE;
    final int power;
    public Ship(String id, double life, double size, int power) {
        this.id = id;
        this.life = life;
        this.size = size;
        this.power = power;
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
        if (Objects.equals(other.getOwnerId(), id)){
            return Optional.of(this);
        }if(other.getType() == EntityType.POWERUP){
            return Optional.of(new Ship(id, life, size, power + 1));
        }if (other.getType() == EntityType.STARSHIP){
            return Optional.of(this);
        }else{
            if (life - other.getDamage() <= 0){
                return Optional.empty();
            }
            return Optional.of(new Ship(id, life - other.getDamage(), size, power));
        }
    }

    @Override
    public double getsize() {
        return size;
    }

    @Override
    public String getOwnerId() {
        return "";
    }

    public double getLife() {
        return life;
    }

    public int getPower() {
        return power;
    }
}
