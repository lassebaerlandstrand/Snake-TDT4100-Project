package Snake.Model;

import javafx.scene.paint.Color;

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
    public String toString() {
        return "SnakeCell [color=" + color + ", x=" + getX() + ", y=" + getY() + "]";
    }

}
