package Snake.Model;

import Snake.Utils.Constants;
import javafx.scene.paint.Color;

// Model
public class Game {

    private Snake snake;
    private int score;
    private boolean gameOver;

    public Game() {
        this.snake = new Snake((int) Math.floor(Constants.COLUMNCOUNT / 2), (int) Math.floor(Constants.ROWCOUNT / 2));
        this.score = 0;
    }

    public Snake getSnake() {
        return this.snake;
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
        if (gameOver)
            return;

        if (snake.nextMoveValid()) {
            snake.move();
        } else {
            gameOver = true;
            snake.setHeadColor(Color.web("#ff0000"));
            snake.setBodyColor(Color.web("#fb5d39"));
        }
    }

}
