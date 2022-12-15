package Movers;

import Entities.Entity;
import Entities.Ship;

import static constant.Constants.*;

public class MoverShip implements Movable{
    public final Ship ship;
    public final double acceleration = SHIP_ACCELERATION;
    public final double xs;
    public final double ys;
    public final double rotation;
    public final Point position;
    public MoverShip(Ship ship, double xs, double ys, double direction, Point position) {
        this.ship = ship;
        this.xs = xs;
        this.ys = ys;
        this.rotation = direction;
        this.position = position;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public double getXs() {
        return xs;
    }

    public double getYs() {
        return ys;
    }

    public double getRotation() {
        return rotation;
    }
    public double getX(){
        return  Math.cos(Math.toRadians(rotation));
    }

    public double getY(){
        return Math.sin(Math.toRadians(rotation));
    }

    public MoverShip update(){
        Point point = verify(new Point(position.getX() + xs, position.getY() + ys));
        return new MoverShip(ship, xs / 1.005, ys / 1.005, rotation, point);
    }

    public Point verify(Point point){
        if (point.getX() < 0){
            return new Point(point.getX() + GAME_WIDTH, point.getY());
        }if (point.getX() > GAME_WIDTH){
            return new Point(point.getX() - GAME_WIDTH, point.getY());
        }if (point.getY() < 0){
            return new Point(point.getX(), point.getY() + GAME_HEIGHT);
        }if (point.getY() > GAME_HEIGHT){
            return new Point(point.getX(), point.getY() - GAME_HEIGHT);
        }else{
            return point;
        }
    }

    public double calculateSpeed(){
        return Math.sqrt(Math.pow(xs, 2) + Math.pow(ys, 2));
    }

    @Override
    public String getId() {
        return ship.getId();
    }

    @Override
    public Entity getEntity() {
        return ship;
    }

    public Point getPosition() {
        return position;
    }

    public Ship getShip() {
        return ship;
    }
}
