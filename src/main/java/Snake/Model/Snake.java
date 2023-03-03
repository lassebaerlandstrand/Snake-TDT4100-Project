package Snake.Model;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

public class Snake {

    List<SnakeCell> snakeCells;
    private int[] direction = new int[] { 0, 1 }; // X, Y - Direction

    // https://colors.artyclick.com/color-names-dictionary/colors/6CBB3C/
    private Color headColor = Color.web("#008000");
    private Color bodyColor = Color.web("#06a106");

    public Snake(int startX, int startY) {
        this.snakeCells = new ArrayList<SnakeCell>();

        SnakeCell headCell = new SnakeCell(startX, startY, headColor);
        snakeCells.add(headCell);

    }

    public int[] getDirection() {
        return direction;
    }

    public void setDirection(int[] direction) throws IllegalArgumentException {
        if (direction.length != 2)
            throw new IllegalArgumentException("Direction must be an array of length 2");
        this.direction = direction;
    }

    public List<SnakeCell> getSnakeCells() {
        return snakeCells;
    }

    public SnakeCell getHead() {
        return snakeCells.get(0);
    }

    private Coordinate getTailPosition() {
        SnakeCell lastCell = snakeCells.get(snakeCells.size() - 1);
        return new Coordinate(lastCell.getX(), lastCell.getY());
    }

    public Coordinate getNextPosition() {
        SnakeCell headCell = snakeCells.get(0);
        return new Coordinate(headCell.getX() + direction[0], headCell.getY() + direction[1]);
    }

    public void grow() {
        SnakeCell cell = new SnakeCell(0, 0, bodyColor);

    }

}
