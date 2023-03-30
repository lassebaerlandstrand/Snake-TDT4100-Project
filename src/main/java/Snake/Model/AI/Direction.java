package Snake.Model.AI;

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
}