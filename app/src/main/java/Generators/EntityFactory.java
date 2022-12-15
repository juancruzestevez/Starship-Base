package Generators;

import Entities.*;
import Entities.Bullet.Bullet;
import Entities.Bullet.BulletType;
import Entities.Bullet.Colition.NormalBulletColition;
import Entities.Bullet.Colition.PiercingColition;
import Entities.weapon.Shoot.*;
import Entities.weapon.Weapon;
import Movers.Mover;
import Movers.MoverShip;
import Movers.Point;
import game.Game;
import game.GameState;
import game.KeyMovement;
import game.ShipController;
import game.controllActions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Generators.RandomNumberGenerator.getRandomNumber;
import static constant.Constants.*;

public class EntityFactory {
    public static Mover createBullet(BulletType bulletType, String ownerId, double angle, Point point) {
        return switch (bulletType) {
            case NORMAL -> new Mover(new Bullet(bulletType, IdGenerator.generateId(EntityType.BULLET), ownerId, new NormalBulletColition(), BULLET_SIZE, BULLET_DAMAGE), point, Math.cos(Math.toRadians(angle))*BULLET_SPEED, Math.sin(Math.toRadians(angle))*BULLET_SPEED);
            case PIERCING -> new Mover(new Bullet(bulletType, IdGenerator.generateId(EntityType.BULLET), ownerId, new PiercingColition(), BULLET_SIZE, BULLET_DAMAGE), point, Math.cos(Math.toRadians(angle))*BULLET_SPEED, Math.sin(Math.toRadians(angle))*BULLET_SPEED);
            case ROCKET -> new Mover(new Bullet(bulletType, IdGenerator.generateId(EntityType.BULLET), ownerId, new NormalBulletColition(), ROCKET_SIZE, ROCKET_DAMAGE), point, Math.cos(Math.toRadians(angle))*BULLET_SPEED / 4, Math.sin(Math.toRadians(angle))*BULLET_SPEED / 4);
        };
    }

    public static Game createGameState(int players){
        Map<String, ShipController> controllers = new HashMap<>();
        Map<String, Double> points = new HashMap<>();
        for (int i = 0; i < players; i++) {
            addNewPlayer(controllers, points);
        }
        return new GameState(1000, 1000, controllers, new HashMap<String, Mover>(), new ArrayList<String>(), points);
    }

    public static void addNewPlayer( Map<String, ShipController> controllers, Map<String, Double> points){
        ShipController newController = createShipcontroller(new Point(SHIP1_ORIGIN_X, PositionGenerator.generatePosition()), SHIP1_ORIGIN_DIRECTION);
        points.put(newController.getId(), 0d);
        controllers.put(newController.getId(), newController);
    }

    public static Weapon createWeapon(ShooterType shooterType, BulletType bulletType){
        return switch (shooterType){
            case NORMAL -> new Weapon(BULLET_SPEED, bulletType, new NormalShoot());
            case CROSS -> new Weapon(BULLET_SPEED, bulletType, new CrossShoot());
        };
    }

    public static ShipController createShipcontroller(Point point, double direction){
        Map<KeyMovement, Action> shipActions = new HashMap<>();
        Map<KeyMovement, Action> entityAction = new HashMap<>();
        shipActions.put(KeyMovement.ACCELERATE, new AccelerateAction());
        shipActions.put(KeyMovement.TURN_LEFT, new Rotate(ROTATION_DEGREES));
        shipActions.put(KeyMovement.TURN_RIGHT, new Rotate(-ROTATION_DEGREES));
        entityAction.put(KeyMovement.SHOOT, new ShootAction());
        shipActions.put(KeyMovement.POWERUP, new PowerUpAction());
        return new ShipController(createShip(point, direction), createWeapon(ShooterType.NORMAL, BulletType.NORMAL), shipActions, entityAction, generateUpgrades());
    }

    public static List<Weapon> generateUpgrades(){
        List<Weapon> upgrades = new ArrayList<>();
        upgrades.add(createWeapon(ShooterType.NORMAL, BulletType.NORMAL));
        upgrades.add(createWeapon(ShooterType.CROSS, BulletType.NORMAL));
        upgrades.add(createWeapon(ShooterType.NORMAL, BulletType.PIERCING));
        upgrades.add(createWeapon(ShooterType.CROSS, BulletType.PIERCING));
        return upgrades;
    }

    public static MoverShip createShip(Point point, double direction){
        return new MoverShip(new Ship(IdGenerator.generateId(EntityType.STARSHIP), LIFE, SHIP_WIDTH, 0), 0,0,direction, point);
    }

    private static Side getRandomSide(){
        Integer side = getRandomNumber(0, 4);
        return switch (side){
            case 0 -> Side.UP;
            case 1 -> Side.RIGHT;
            case 2 -> Side.DOWN;
            case 3 -> Side.LEFT;
            default -> Side.RIGHT;
        };
    }

    public static Mover spawnEntity(Point targetPosition, Entity entity){
        Side randomSide = getRandomSide();
        return switch (randomSide) {
            case UP -> generateMovementVector(new Point(getRandomNumber(0, (int) GAME_WIDTH), GAME_HEIGHT), targetPosition, entity);
            case DOWN -> generateMovementVector(new Point(getRandomNumber(0, (int) GAME_WIDTH), 0), targetPosition, entity);
            case LEFT -> generateMovementVector(new Point(0, getRandomNumber(0, (int) GAME_HEIGHT)), targetPosition, entity);
            case RIGHT -> generateMovementVector(new Point(GAME_WIDTH, getRandomNumber(0, (int) GAME_HEIGHT)), targetPosition, entity);
        };
    }


    public static Mover spawnPowerUp(Point targetPosition){
        Side randomSide = getRandomSide();
        return switch (randomSide) {
            case UP -> generateMovementVector(new Point(getRandomNumber(0, (int) GAME_WIDTH), GAME_HEIGHT), targetPosition, new PowerUp(IdGenerator.generateId(EntityType.POWERUP), POWERUP_SIZE));
            case DOWN -> generateMovementVector(new Point(getRandomNumber(0, (int) GAME_WIDTH), 0), targetPosition, new PowerUp(IdGenerator.generateId(EntityType.POWERUP), POWERUP_SIZE));
            case LEFT -> generateMovementVector(new Point(0, getRandomNumber(0, (int) GAME_HEIGHT)), targetPosition, new PowerUp(IdGenerator.generateId(EntityType.POWERUP), POWERUP_SIZE));
            case RIGHT -> generateMovementVector(new Point(GAME_WIDTH, getRandomNumber(0, (int) GAME_HEIGHT)), targetPosition, new PowerUp(IdGenerator.generateId(EntityType.POWERUP), POWERUP_SIZE));
        };
    }

    private static Mover generateMovementVector(Point positionStart , Point positionTo, Entity entity) {
        return new Mover(entity, positionStart, (positionTo.getX() - positionStart.getX()) * ASTEROID_SPEED, (positionTo.getY() - positionStart.getY()) * ASTEROID_SPEED);
    }

    public static Point generateRandomPoint(){
        return new Point(Math.random() * GAME_WIDTH, Math.random() * GAME_HEIGHT);
    }

    public static Asteroid createAsteroid(){
        return new Asteroid(IdGenerator.generateId(EntityType.ASTEROID), RandomNumberGenerator.getRandomNumber(MIN_ASTEROID_SIZE, MAX_ASTEROID_SIZE).doubleValue());
    }

    public static PowerUp createPowerUp(){
        return new PowerUp(IdGenerator.generateId(EntityType.POWERUP), POWERUP_SIZE);
    }
}
