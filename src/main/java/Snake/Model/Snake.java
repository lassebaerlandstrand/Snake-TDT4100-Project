package Snake.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import Snake.Utils.Constants;
import javafx.scene.paint.Color;

public class Snake {

    List<SnakeCell> snakeCells;
    private DirectionDefaultList directionQueue = new DirectionDefaultList(new int[] { 0, 0 });

    private Color headColor = Color.web("#008000");
    private Color bodyColor = Color.web("#07bc07");

    public Snake(int startX, int startY) {
        if (startX < 0 || startX >= Constants.COLUMNCOUNT || startY < 0 || startY >= Constants.ROWCOUNT)
            throw new IllegalArgumentException("Start position must be within the grid");

        this.snakeCells = new ArrayList<SnakeCell>();
        SnakeCell headCell = new SnakeCell(startX, startY, headColor);
        snakeCells.add(headCell);
    }

    // Deep copy snake
    public Snake(Snake snake) {
        this.snakeCells = snake.getSnakeCells().stream().map(cell -> new SnakeCell(cell)).collect(Collectors.toList());
        this.directionQueue = new DirectionDefaultList(snake.getDirectionPeek()); // Not excatly a deep copy, but sufficient for this use case
    }

    public int[] getDirectionPeek() {
        return directionQueue.peek();
    }

    public int[] getDirection() {
        return directionQueue.getRemoveFirst();
    }

    public int[] getLastDirectionPeek() {
        return directionQueue.peekLast();
    }

    public int getDirectionSize() {
        return directionQueue.size();
    }

    public void addDirection(int[] direction) throws IllegalArgumentException {
        if (direction.length != 2)
            throw new IllegalArgumentException("Direction must be an array of length 2");
        directionQueue.add(direction);
    }

    public List<SnakeCell> getSnakeCells() {
        return snakeCells;
    }

    public List<Coordinate> getSnakeCoordinates() {
        return snakeCells.stream().map(cell -> cell.getPos()).collect(Collectors.toList());
    }

    private void addToSnakeCells(int index, SnakeCell cell) {
        snakeCells.add(index, cell);
    }

    // TODO: DELETE THIS; ONLY USED FOR TESTING
    public void appendSnakeCells(SnakeCell cell) {
        snakeCells.add(cell);
    }

    private void replaceSnakeCell(int index, SnakeCell cell) {
        snakeCells.set(index, cell);
    }

    public SnakeCell getHead() {
        return snakeCells.get(0);
    }

    public int getLength() {
        return snakeCells.size();
    }

    public Coordinate nextPositionPeek() {
        return new Coordinate(getHead().getX() + getDirectionPeek()[0], getHead().getY() + getDirectionPeek()[1]);
    }

    private Coordinate nextPosition() {
        int[] direction = getDirection();
        return new Coordinate(getHead().getX() + direction[0], getHead().getY() + direction[1]);
    }

    public boolean nextMoveValid() {
        if (Arrays.equals(directionQueue.peek(), new int[] { 0, 0 }))
            return true;
        Coordinate nextPos = nextPositionPeek();

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

    public boolean nextMoveValid(int[] direction) {
        if (Arrays.equals(direction, new int[] { 0, 0 }))
            return true;
        Coordinate nextPos = new Coordinate(getHead().getX() + direction[0], getHead().getY() + direction[1]);

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

    public void move(boolean grow) {
        if (!nextMoveValid()) {
            throw new IllegalStateException("Cannot move snake to invalid position");
        }
        SnakeCell headCell = getHead();
        Coordinate headPos = new Coordinate(headCell.getX(), headCell.getY());
        Coordinate nextPos = nextPosition();

        // Move the head
        headCell.setX(nextPos.getX());
        headCell.setY(nextPos.getY());
        replaceSnakeCell(0, headCell);

        // Insert new cell where head previously was
        if (getLength() > 1 || (grow && getLength() == 1)) {
            SnakeCell newSnakeCell = new SnakeCell(headPos.getX(), headPos.getY(), bodyColor);
            addToSnakeCells(1, newSnakeCell);
        }

        // Don't remove tail if we are growing
        if (getLength() > 1 && !grow)
            snakeCells.remove(getLength() - 1);
    }

    public void setHeadColor(Color color) {
        this.headColor = color;

        // Since the color is passed by value, we need to update the color of the head cell
        getHead().setColor(color);
    }

    public void setBodyColor(Color color) {
        this.bodyColor = color;

        // Since the color is passed by value, we need to update the color of the cells by looping through them
        getSnakeCells().stream().skip(1).forEach(cell -> cell.setColor(color));
    }

}
