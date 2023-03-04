package Snake.Model;

import javafx.scene.paint.Color;

public class Food extends Coordinate {

    private boolean isEaten = false;
    private Color color = Color.RED;

    public Food(int x, int y) {
        super(x, y);
    }

    public Food(Coordinate coord) {
        super(coord.getX(), coord.getY());
    }

    public Coordinate getPos() {
        return new Coordinate(getX(), getY());
    }

    // Not needed
    public boolean isEaten() {
        return isEaten;
    }

    public Color getColor() {
        return color;
    }

}
