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
            case NORMAL -> new Mover(new Bullet(bulletType, IdGenerator.generateId(EntityType.BULLET), ownerId, new NormalBulletColition(), BULLET_SIZE), point, Math.cos(Math.toRadians(angle))*BULLET_SPEED, Math.sin(Math.toRadians(angle))*BULLET_SPEED);
            case PIERCING -> new Mover(new Bullet(bulletType, IdGenerator.generateId(EntityType.BULLET), ownerId, new PiercingColition(), BULLET_SIZE), point, Math.cos(Math.toRadians(angle))*BULLET_SPEED, Math.sin(Math.toRadians(angle))*BULLET_SPEED);
            case ROCKET -> new Mover(new Bullet(bulletType, IdGenerator.generateId(EntityType.BULLET), ownerId, new NormalBulletColition(), BULLET_SIZE), point, Math.cos(Math.toRadians(angle))*BULLET_SPEED / 4, Math.sin(Math.toRadians(angle))*BULLET_SPEED / 4);
        };
    }

    public static Game createGameState2Players(){
        Map<String, ShipController> controllers = new HashMap<>();
        Map<String, Integer> points = new HashMap<>();
        ShipController newController = createShipcontroller(new Point(200, 200));
        ShipController newController2 = createShipcontroller(new Point(800, 200));
        points.put(newController.getId(), 0);
        controllers.put(newController.getId(), newController);
        points.put(newController2.getId(), 0);
        controllers.put(newController2.getId(), newController2);
        return new GameState(1000, 1000, controllers, new HashMap<String, Mover>(), new ArrayList<String>(), points);
    }

    public static Game createGameState(){
        Map<String, ShipController> controllers = new HashMap<>();
        Map<String, Integer> points = new HashMap<>();
        ShipController newController = createShipcontroller(new Point(200, 200));
        points.put(newController.getId(), 0);
        controllers.put(newController.getId(), newController);
        return new GameState(1000, 1000, controllers, new HashMap<String, Mover>(), new ArrayList<String>(), points);
    }

    public static Weapon createWeapon(ShooterType shooterType, BulletType bulletType){
        return switch (shooterType){
            case NORMAL -> new Weapon(BULLET_SPEED, bulletType, new NormalShoot());
            case CROSS -> new Weapon(BULLET_SPEED, bulletType, new CrossShoot());
            case TRIPLE -> new Weapon(BULLET_SPEED, bulletType, new ThreeBulletShoot());
        };
    }

    public static ShipController createShipcontroller(Point point){
        Map<KeyMovement, Action> shipActions = new HashMap<>();
        Map<KeyMovement, Action> entityAction = new HashMap<>();
        shipActions.put(KeyMovement.ACCELERATE, new AccelerateAction());
        shipActions.put(KeyMovement.TURN_LEFT, new Rotate(ROTATION_DEGREES));
        shipActions.put(KeyMovement.TURN_RIGHT, new Rotate(-ROTATION_DEGREES));
        entityAction.put(KeyMovement.SHOOT, new ShootAction());
        shipActions.put(KeyMovement.POWERUP, new PowerUpAction());
        return new ShipController(createShip(point), createWeapon(ShooterType.NORMAL, BulletType.NORMAL), shipActions, entityAction, generateUpgrades());
    }

    public static List<Weapon> generateUpgrades(){
        List<Weapon> upgrades = new ArrayList<>();
        upgrades.add(createWeapon(ShooterType.NORMAL, BulletType.NORMAL));
        upgrades.add(createWeapon(ShooterType.CROSS, BulletType.NORMAL));
        upgrades.add(createWeapon(ShooterType.TRIPLE, BulletType.NORMAL));
        upgrades.add(createWeapon(ShooterType.NORMAL, BulletType.PIERCING));
        upgrades.add(createWeapon(ShooterType.CROSS, BulletType.PIERCING));
        upgrades.add(createWeapon(ShooterType.TRIPLE, BulletType.PIERCING));
        return upgrades;
    }

    public static MoverShip createShip(Point point){
        return new MoverShip(new Ship(IdGenerator.generateId(EntityType.STARSHIP), 30d, SHIP_WIDTH, LIVES, 0), 0,0,0, point);
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

    public static Mover spawnAsteroid(Point targetPosition){
        Side randomSide = getRandomSide();
        return switch (randomSide) {
            case UP -> generateMovementVector(new Point(getRandomNumber(0, (int) GAME_WIDTH), GAME_HEIGHT), targetPosition, new Asteroid(IdGenerator.generateId(EntityType.ASTEROID), MAX_ASTEROID_SIZE));
            case DOWN -> generateMovementVector(new Point(getRandomNumber(0, (int) GAME_WIDTH), 0), targetPosition, new Asteroid(IdGenerator.generateId(EntityType.ASTEROID), MAX_ASTEROID_SIZE));
            case LEFT -> generateMovementVector(new Point(0, getRandomNumber(0, (int) GAME_HEIGHT)), targetPosition, new Asteroid(IdGenerator.generateId(EntityType.ASTEROID), MAX_ASTEROID_SIZE));
            case RIGHT -> generateMovementVector(new Point(GAME_WIDTH, getRandomNumber(0, (int) GAME_HEIGHT)), targetPosition, new Asteroid(IdGenerator.generateId(EntityType.ASTEROID), MAX_ASTEROID_SIZE));
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

    public static Mover spawnEntity(Point targetPosition, EntityType entityType){
        if (entityType == EntityType.ASTEROID){
            return spawnAsteroid(targetPosition);
        }if (entityType == EntityType.POWERUP){
            return spawnPowerUp(targetPosition);
        }else {
            return null;
        }
    }

    private static Mover generateMovementVector(Point positionStart , Point positionTo, Entity entity) {
        return new Mover(entity, positionStart, (positionTo.getX() - positionStart.getX()) * ASTEROID_SPEED, (positionTo.getY() - positionStart.getY()) * ASTEROID_SPEED);
    }

    public static Point generateRandomPoint(){
        return new Point(Math.random() * GAME_WIDTH, Math.random() * GAME_HEIGHT);
    }
}
