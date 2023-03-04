package Snake.Model;

import javafx.scene.paint.Color;

public class SnakeCell extends Coordinate {

    private Color color;

    public SnakeCell(int x, int y, Color color) {
        super(x, y);
        this.color = color;
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
