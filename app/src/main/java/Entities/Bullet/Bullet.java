package Entities.Bullet;

import constant.Constants;
import Entities.*;
import Entities.Bullet.Colition.BulletColition;

import java.util.List;
import java.util.Optional;

public class Bullet implements Entity {
    final Double damage;

    final BulletType bulletType;

    final String id;

    final String ownerId;

    final EntityType entityType = EntityType.BULLET;

    final BulletColition bulletColition;

    final double size;



    public Bullet( BulletType bulletType, String id, String ownerId, BulletColition bulletColition, double size, double damage) {
        this.bulletColition = bulletColition;
        this.damage = damage;
        this.bulletType = bulletType;
        this.id = id;
        this.ownerId = ownerId;
        this.size = size;
    }

    public String getOwnerId() {
        return ownerId;
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
        return bulletColition.collide(this, other);
    }

    @Override
    public double getsize() {
        return size;
    }

}
