package Snake.Model;

import java.util.ArrayList;
import java.util.List;

import Snake.Utils.Constants;
import javafx.scene.paint.Color;

public class Snake {

    List<SnakeCell> snakeCells;
    private int[] direction = new int[] { 0, 1 }; // X, Y - Direction

    private Color headColor = Color.web("#008000");
    private Color bodyColor = Color.web("#07bc07");

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

    private void addToSnakeCells(int index, SnakeCell cell) {
        snakeCells.add(index, cell);
    }

    private void replaceSnakeCell(int index, SnakeCell cell) {
        snakeCells.set(index, cell);
    }

    public SnakeCell getHead() {
        return snakeCells.get(0);
    }

    public boolean nextMoveValid() {
        SnakeCell headCell = getHead();
        Coordinate headPos = new Coordinate(headCell.getX(), headCell.getY());

        Coordinate nextPos = new Coordinate(headPos.getX() + direction[0], headCell.getY() + direction[1]);

        // Hit wall
        if (nextPos.getX() < 0 || nextPos.getX() >= Constants.COLUMNCOUNT || nextPos.getY() < 0
                || nextPos.getY() >= Constants.ROWCOUNT) {
            return false;
        }
        // Hit self
        if (snakeCells.stream().anyMatch(cell -> cell.getX() == nextPos.getX() && cell.getY() == nextPos.getY())) {
            return false;
        }

        return true;
    }

    public void move() {

        SnakeCell headCell = getHead();
        Coordinate headPos = new Coordinate(headCell.getX(), headCell.getY());

        // Get next position
        Coordinate nextPos = new Coordinate(headPos.getX() + direction[0], headCell.getY() + direction[1]);

        // Move the head
        headCell.setX(nextPos.getX());
        headCell.setY(nextPos.getY());
        replaceSnakeCell(0, headCell);

        // Insert new cell where head previously was
        if (snakeCells.size() > 1) {
            SnakeCell newSnakeCell = new SnakeCell(headPos.getX(), headPos.getY(), bodyColor);
            addToSnakeCells(1, newSnakeCell);
        }

        // Remove the tail if apple is eaten
        // if (!appleEaten) {
        if (snakeCells.size() > 1)
            snakeCells.remove(snakeCells.size() - 1);
    }

    public void setHeadColor(Color color) {
        this.headColor = color;

        // Since the color is passed by value, we need to update the color of the head
        // cell
        getHead().setColor(color);
    }

    public void setBodyColor(Color color) {
        this.bodyColor = color;

        // Since the color is passed by value, we need to update the color of the cells
        // by looping through them
        getSnakeCells().stream().skip(1).forEach(cell -> cell.setColor(color));
    }

}
