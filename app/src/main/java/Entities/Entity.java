package Entities;

import Entities.Bullet.Bullet;

import java.util.List;
import java.util.Optional;

public interface Entity{
    public String getId();
    public EntityType getType();
    public double getDamage();
    public Optional<Entity> collide(Entity other);
    double getsize();

}
