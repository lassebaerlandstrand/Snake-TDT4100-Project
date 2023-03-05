package Snake.Controller;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Snake.Model.Game;
import Snake.Utils.Constants;
import Snake.View.GameView;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

// Controller
public class FXMLController {

    private Game game;
    private GameView gameView;

    Runnable frameUpdate = new Runnable() {
        @Override
        public void run() {
            game.update();
            gameView.drawFrame(canvas, game);
        }
    };

    @FXML
    private Canvas canvas;

    @FXML
    private Text scoreText;

    public FXMLController() {
        this.game = new Game();
        this.gameView = new GameView();
    }

    @FXML
    protected void initialize() {
        canvas.requestFocus();
        gameView.drawBackground(canvas, game);
        gameView.drawFrame(canvas, game);
        scoreText.setText(String.valueOf(game.getScore()));

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(frameUpdate, 0, (long) Constants.FRAMETIME.toMillis(), TimeUnit.MILLISECONDS);
    }

    @FXML
    private void keyListener(KeyEvent key) {
        switch (key.getCode()) {
            case W:
            case UP:
                if (game.getSnake().getCurrentFrameDirection()[1] == -1 && game.getSnake().getLength() > 1) // Cannot reverse back into itself
                    break;
                game.getSnake().setNextFrameDirection(new int[] { 0, 1 });
                break;
            case S:
            case DOWN:
                if (game.getSnake().getCurrentFrameDirection()[1] == 1 && game.getSnake().getLength() > 1)
                    break;
                game.getSnake().setNextFrameDirection(new int[] { 0, -1 });
                break;
            case A:
            case LEFT:
                if (game.getSnake().getCurrentFrameDirection()[0] == 1 && game.getSnake().getLength() > 1)
                    break;
                game.getSnake().setNextFrameDirection(new int[] { -1, 0 });
                break;
            case D:
            case RIGHT:
                if (game.getSnake().getCurrentFrameDirection()[0] == -1 && game.getSnake().getLength() > 1)
                    break;
                game.getSnake().setNextFrameDirection(new int[] { 1, 0 });
                break;
            default:
                break;
        }
    }

    @FXML
    private void handleHighScoreButton() {
        System.out.println("High Score Button Pressed");
    }

}
