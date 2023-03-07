package Snake.Controller;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Snake.App;
import Snake.Data.Highscore;
import Snake.Model.Game;
import Snake.Utils.Constants;
import Snake.View.GameView;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

// Controller
public class FXMLController implements Controller {

    private Game game;
    private GameView gameView;

    @FXML
    private Canvas canvas;

    @FXML
    private Text scoreText;

    @FXML
    private Text highscoreText;

    public FXMLController() {
        this.game = new Game(this);
        this.gameView = new GameView();
    }

    Runnable frameUpdate = new Runnable() {
        @Override
        public void run() {
            game.update();
            gameView.drawFrame(canvas, game);
        }
    };

    @FXML
    protected void initialize() {
        canvas.requestFocus();
        gameView.resetView(canvas, game);
        setScoreText(game.getScore());
        setHighscoreText(new Highscore("highscore.txt").getHighScore());

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(frameUpdate, 0, (long) Constants.FRAMETIME.toMillis(), TimeUnit.MILLISECONDS);
    }

    @FXML
    private void keyListener(KeyEvent key) {
        if (game.getGameOver()) {
            restartGame();
            return;
        }
        switch (key.getCode()) {
            case W:
            case UP:
                if (game.getSnake().getLastDirectionPeek()[1] == -1 && game.getSnake().getLength() > 1)
                    break;
                game.getSnake().addDirection(new int[] { 0, 1 });
                break;
            case S:
            case DOWN:
                if (game.getSnake().getLastDirectionPeek()[1] == 1 && game.getSnake().getLength() > 1)
                    break;
                game.getSnake().addDirection(new int[] { 0, -1 });
                break;
            case A:
            case LEFT:
                if (game.getSnake().getLastDirectionPeek()[0] == 1 && game.getSnake().getLength() > 1)
                    break;
                game.getSnake().addDirection(new int[] { -1, 0 });
                break;
            case D:
            case RIGHT:
                if (game.getSnake().getLastDirectionPeek()[0] == -1 && game.getSnake().getLength() > 1)
                    break;
                game.getSnake().addDirection(new int[] { 1, 0 });
                break;
            default:
                break;
        }
    }

    public void setScoreText(int score) {
        scoreText.setText(String.valueOf(score));
    }

    public void setHighscoreText(int score) {
        if (score > Integer.parseInt(highscoreText.getText()))
            highscoreText.setText(String.valueOf(score));
    }

    @FXML
    private void switchToHighscore() throws IOException {
        App.setRoot("Highscore");
    }

    private void restartGame() {
        game = new Game(this);
        gameView.resetView(canvas, game);
        setScoreText(game.getScore());
    }

}
