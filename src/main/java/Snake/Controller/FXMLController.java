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
            // gameView.drawGame();
            // System.out.println("Hello from " + Thread.currentThread().getName());
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
        gameView.drawFrame(canvas, game);
        scoreText.setText(String.valueOf(game.getScore()));

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(frameUpdate, 0, (long) Constants.FRAMETIME.toMillis(), TimeUnit.MILLISECONDS);
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
