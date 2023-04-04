package Snake.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import Snake.Controller.ControllerListener;
import Snake.Data.HighScore;
import Snake.Model.AI.GameAI;
import Snake.Utils.Constants;
import Snake.Utils.Coordinate;
import javafx.scene.paint.Color;

/**
 * Main model class for the Snake game. This class handles all the game logic, and keeps other objects updated.
 */
public class Game {

    private Snake snake;
    private int score;
    private boolean gameOver;
    private Food food;
    private boolean paused = false;
    private ControllerListener controller;
    private HighScore highScore;

    // Save a copy of allPositions to avoid creating a new list every time
    private static List<Coordinate> allPositions = IntStream.range(0, Constants.COLUMNCOUNT)
            .mapToObj(x -> IntStream.range(0, Constants.ROWCOUNT).mapToObj(y -> new Coordinate(x, y)))
            .flatMap(x -> x).collect(Collectors.toList());

    public Game() {
        this.snake = new Snake((int) Math.floor(Math.random() * Constants.COLUMNCOUNT),
                (int) Math.floor(Math.random() * Constants.ROWCOUNT));
        this.score = 0;
        highScore = new HighScore("highscore.txt");
        food = new Food(getRandomAvailablePosition());
    }

    public Game(boolean allowDuplicatesInputs) {
        this.snake = new Snake((int) Math.floor(Math.random() * Constants.COLUMNCOUNT),
                (int) Math.floor(Math.random() * Constants.ROWCOUNT), allowDuplicatesInputs);
        this.score = 0;
        highScore = new HighScore("highscore.txt");
        food = new Food(getRandomAvailablePosition());
    }

    public Game(ControllerListener controller) {
        this();
        this.controller = controller;
    }

    public Game(ControllerListener controller, boolean allowDuplicatesInputs) {
        this(allowDuplicatesInputs);
        this.controller = controller;
    }

    public Snake getSnake() {
        return this.snake;
    }

    public Food getFood() {
        return this.food;
    }

    public boolean isGameOver() {
        return this.gameOver;
    }

    public int getScore() {
        return this.score;
    }

    public void increaseScore() {
        this.score++;

        // Observable
        if (controller != null) {
            controller.setScoreText(score);
            if (score >= highScore.getHighScore())
                controller.setHighscoreText(score);
        }
    }

    public void update() {
        if (gameOver || paused)
            return;

        if (snake.nextMoveValid()) {
            boolean ateFood = snake.nextPositionPeek().equals(food.getPos());
            if (ateFood) {
                increaseScore();
                food = new Food(getRandomAvailablePosition());
            }
            snake.move(ateFood);
        } else {
            gameOver = true;
            String playerType = this instanceof GameAI ? "AI" : "Human";
            highScore.addScore(score, playerType);
            snake.setHeadColor(Color.web("#ff0000"));
            snake.setBodyColor(Color.web("#fb5d39"));
            if (controller != null)
                controller.setGameOver();
        }
    }

    public boolean getPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
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

    public int getHighScore() {
        return Math.max(score, highScore.getHighScore());
    }

}
