package Snake.Controller;

import Snake.Model.Game;
import Snake.View.GameView;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;

// Controller
public class FXMLController {

    private Game game;
    private GameView gameView;

    @FXML
    private Canvas canvas;

    public FXMLController() {
        this.game = new Game();
        this.gameView = new GameView();
    }

    @FXML
    protected void initialize() {
        canvas.requestFocus();
        gameView.draw(canvas, game);
    }

    @FXML
    private void keyListener(KeyEvent key) {
        switch (key.getCode()) {
            case UP:
                game.getSnake().setDirection(new int[] { 0, 1 });
                break;
            case DOWN:
                game.getSnake().setDirection(new int[] { 0, -1 });
                break;
            case LEFT:
                game.getSnake().setDirection(new int[] { -1, 0 });
                break;
            case RIGHT:
                game.getSnake().setDirection(new int[] { 1, 0 });
                break;
            default:
                break;
        }
    }

}
