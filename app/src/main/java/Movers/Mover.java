package Movers;

import Entities.Entity;

public class Mover implements Movable {
    public final Entity entity;

    public final Point point;
    public final double xs;
    public final double ys;

    public Mover(Entity entity, Point point, double xs, double ys) {
        this.entity = entity;
        this.point = point;
        this.xs = xs;
        this.ys = ys;
    }

    public double getXs() {
        return xs;
    }

    public double getYs() {
        return ys;
    }

    public Mover update(){
        return new Mover(entity, new Point(point.getX() + xs, point.getY() + ys), xs, ys);
    }

    @Override
    public Point getPosition() {
        return point;
    }

    @Override
    public double calculateSpeed() {
        return 0;
    }

    @Override
    public String getId() {
        return entity.getId();
    }


    @Override
    public Entity getEntity() {
        return entity;
    }

    @Override
    public double getRotation() {
        return 0;
    }

    public double calculateDegree(){
        return Math.toDegrees(Math.atan(ys/xs));
    }

}