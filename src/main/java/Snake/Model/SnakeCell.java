package Snake;

import javafx.scene.paint.Color;

public class SnakeCell {

    private Color color;

    // Coordinates in grid
    private int x;
    private int y;

    public SnakeCell(int x, int y, Color color) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
