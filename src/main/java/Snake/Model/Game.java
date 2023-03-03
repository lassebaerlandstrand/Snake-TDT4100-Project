package Snake.Model;

import Snake.Utils.Constants;

// Model
public class Game {

    private Snake snake;
    private int score;
    private boolean gameOver;

    public Game() {
        this.snake = new Snake((int) Math.floor(Constants.columnCount / 2), (int) Math.floor(Constants.rowCount / 2));
        this.score = 0;
    }

    public Snake getSnake() {
        return this.snake;
    }

    public void update() {
        System.out.println("Update");
    }

}
