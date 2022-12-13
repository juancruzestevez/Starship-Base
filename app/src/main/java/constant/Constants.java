package constant;

public class Constants {
    public static final double GAME_WIDTH = 1000;
    public static final double GAME_HEIGHT = 1000;

    //SHIP CONSTANTS
    public static final double SHIP_WIDTH = 50;
    public static final double SHIP_HEIGHT = 50;
    public static final int LIVES = 3;
    public static final double SHIP_ACCELERATION = 0.2;
    public static final double ROTATION_DEGREES = 10;
    public static final double STARTING_ANGLE = 90;

    //BULLET CONSTANTS
    public static final int BULLETS_PER_SHOT = 1;
    public static final double BULLET_SPEED = 10;
    public static final int BULLET_SIZE = 10;
    public static final double BULLET_DAMAGE = 10;
    public static final int ROCKET_DAMAGE = 50;
    public static final int ROCKET_SIZE = 20;

    //ASTEROID CONSTANTS
    public static final int MAX_ASTEROID_SIZE = 100;
    public static final int MIN_ASTEROID_SIZE = 25;

    public static final double ASTEROID_SPEED = 0.003;
    public static final double SPAWN_PROBABILITY = 0.002;



    public static final double POWERUP_SIZE = 10;



    //KEY CONSTANTS
    public static final String PAUSE_GAME = "P";
    public static final String RESUME_GAME = "R";
    public static final String SAVE_GAME = "G";
}
