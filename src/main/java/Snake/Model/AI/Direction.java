package Snake.Model.AI;

import java.util.Arrays;
import java.util.Random;

final class Direction {
    private int[] direction;
    private int availableArea;
    private int distance;
    private boolean foodEaten;

    public Direction(int[] direction, int availableArea, int distance, boolean foodEaten) {
        this.direction = direction;
        this.availableArea = availableArea;
        this.distance = distance;
        this.foodEaten = foodEaten;
    }

    public int[] getDirection() {
        return direction;
    }

    public int getAvailableArea() {
        return availableArea;
    }

    public int getDistance() {
        return distance;
    }

    public boolean getFoodEaten() {
        return foodEaten;
    }

    public static int[] getRandomDirection() {
        int dirX = Arrays.asList(-1, 0, 1).get(new Random().nextInt(3));
        int dirY = dirX != 0 ? 0 : Arrays.asList(-1, 1).get(new Random().nextInt(2));
        return new int[] { dirX, dirY };
    }
}