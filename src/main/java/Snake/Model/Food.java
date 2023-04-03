package Snake.Model;

import Snake.Utils.Coordinate;
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

    // Copy
    public Food(Food food) {
        super(food.getX(), food.getY());
        this.isEaten = food.isEaten();
        this.color = food.getColor();
    }

    public Coordinate getPos() {
        return new Coordinate(getX(), getY());
    }

    public boolean isEaten() {
        return isEaten;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object arg0) {
        if (!(arg0 instanceof Food)) {
            return false;
        }
        return this.getX() == ((Coordinate) arg0).getX() && this.getY() == ((Coordinate) arg0).getY()
                && this.color == ((Food) arg0).getColor();
    }

}
