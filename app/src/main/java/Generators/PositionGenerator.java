package Generators;

public class PositionGenerator {
    public static int currentPosition = 100;

    public static double generatePosition() {
        currentPosition += 60;
        return currentPosition;
    }
}
