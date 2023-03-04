package Snake.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Snake.Utils.Constants;
import javafx.scene.paint.Color;

// Model
public class Game {

    private Snake snake;
    private int score;
    private boolean gameOver;
    private Food food;
    private boolean paused = false;

    public int frameCounter = 0;

    // Save a copy of allPositions to avoid creating a new list every time
    private List<Coordinate> allPositions;

    public Game() {
        this.snake = new Snake((int) Math.floor(Constants.COLUMNCOUNT / 2), (int) Math.floor(Constants.ROWCOUNT / 2));
        this.score = 0;

        allPositions = new ArrayList<Coordinate>();
        for (int x = 0; x < Constants.COLUMNCOUNT; x++) {
            for (int y = 0; y < Constants.ROWCOUNT; y++) {
                allPositions.add(new Coordinate(x, y));
            }
        }

        food = new Food(getRandomAvailablePosition());
    }

    public Snake getSnake() {
        return this.snake;
    }

    public Food getFood() {
        return this.food;
    }

    public boolean getGameOver() {
        return this.gameOver;
    }

    public int getScore() {
        return this.score;
    }

    public void increaseScore() {
        this.score++;
        // Observable
    }

    public void update() {
        frameCounter++;
        if (gameOver || paused)
            return;

        if (snake.nextMoveValid()) {
            boolean ateFood = snake.nextPosition().equals(food.getPos());
            if (ateFood) {
                increaseScore();
                food = new Food(getRandomAvailablePosition());
            }
            snake.move(ateFood);
            snake.setCurrentFrameDirection(snake.getNextFrameDirection());
        } else {
            gameOver = true;
            snake.setHeadColor(Color.web("#ff0000"));
            snake.setBodyColor(Color.web("#fb5d39"));
        }
    }

    public Coordinate getRandomAvailablePosition() {
        List<Coordinate> availablePositions = getAvailablePositions();
        int randomIndex = (int) Math.floor(Math.random() * availablePositions.size());
        return availablePositions.get(randomIndex);
    }

    public List<Coordinate> getAvailablePositions() {
        List<Coordinate> allPositionsCopy = new ArrayList<Coordinate>(allPositions);

        List<Coordinate> snakeCoordinates = snake.getSnakeCells().stream()
                .map(cell -> cell.getPos()).collect(Collectors.toList());

        allPositionsCopy.removeAll(snakeCoordinates);
        return allPositionsCopy;

    }

}
