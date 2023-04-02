package Snake.Model;

import Snake.Utils.Coordinate;
import javafx.scene.paint.Color;

/**
 * A cell in the snake, contains a coordinate and a color
 */
public class SnakeCell extends Coordinate {

    private Color color;

    public SnakeCell(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    public SnakeCell(SnakeCell snakeCell) {
        super(snakeCell.getX(), snakeCell.getY());
        this.color = snakeCell.getColor();
    }

    public Coordinate getPos() {
        return new Coordinate(getX(), getY());
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object arg0) {
        if (!(arg0 instanceof SnakeCell)) {
            return false;
        }
        return this.getX() == ((Coordinate) arg0).getX() && this.getY() == ((Coordinate) arg0).getY()
                && this.color == ((SnakeCell) arg0).getColor();
    }

    @Override
    public String toString() {
        return "SnakeCell [color=" + color + ", x=" + getX() + ", y=" + getY() + "]";
    }

}
