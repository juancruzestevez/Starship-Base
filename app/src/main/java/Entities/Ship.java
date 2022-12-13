package Entities;

import java.util.Optional;

public final class Ship implements Entity{
    final String id;
    final EntityType entityType = EntityType.STARSHIP;
    final double life;
    final int lives;
    final double size;
    final int power;
    public Ship(String id, double life, double size, int lives, int power) {
        this.id = id;
        this.life = life;
        this.size = size;
        this.lives = lives;
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
        if (other.getType() == EntityType.ASTEROID){
            if (lives == 0){
                return Optional.empty();
            }else{
                return Optional.of(new Ship(id, life, size, lives - 1, power));
            }
        }if(other.getType() == EntityType.POWERUP){
            return Optional.of(new Ship(id, life, size, lives, power + 1));
        }
        else{
            return Optional.of(this);
        }
    }

    @Override
    public double getsize() {
        return size;
    }

    public int getLives() {
        return lives;
    }

    public double getLife() {
        return life;
    }

    public int getPower() {
        return power;
    }
}
