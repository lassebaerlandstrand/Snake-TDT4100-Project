package Snake.Controller;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Snake.App;
import Snake.Model.Game;
import Snake.Model.AI.GameAI;
import Snake.Utils.Constants;
import Snake.View.GameView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

/** 
 * Controller class for the Snake game. This class handles all the user input, and communicates with the model and the view.
 */
public class FXMLController implements ControllerListener {

    private Game game;
    private GameView gameView;
    private Date gameOverTimestamp;
    private ScheduledExecutorService executor;

    private boolean activatedAI = false;

    @FXML
    private Canvas canvas;

    @FXML
    private Text scoreText;

    @FXML
    private Text highscoreText;

    @FXML
    private Pane gameOverPane;
    @FXML
    private Text gameOverScore;
    @FXML
    private Text gameOverHighscore;

    @FXML
    private Button toggleSnakeButton;

    public FXMLController() {
        this.game = new Game(this);
        this.gameView = new GameView();
        executor = Executors.newScheduledThreadPool(1);
    }

    Runnable frameUpdate = () -> {
        Platform.runLater(() -> {
            game.update();
            gameView.drawFrame(canvas, game);
        });
    };

    @FXML
    protected void initialize() {
        canvas.requestFocus();
        gameView.drawInitial(canvas, game);
        setScoreText(game.getScore());
        setHighscoreText(game.getHighScore());

        executor.scheduleAtFixedRate(frameUpdate, 0, (long) Constants.FRAMETIME.toMillis(), TimeUnit.MILLISECONDS);
    }

    @FXML
    private void keyListener(KeyEvent key) {
        if (key.getCode() == KeyCode.SPACE) {
            game.setPaused(!game.getPaused());
            return;
        }

        if (game.getGameOver()) {
            // Cooldown for when to restart game, this is because one could press a key right after death, and consequently skip game over screen
            if (getTimeBetweenGameOver() > 800)
                restartGame();
            return;
        }

        if (game instanceof GameAI) {
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

    @Override
    public void setScoreText(int score) {
        scoreText.setText(String.valueOf(score));
    }

    @Override
    public void setHighscoreText(int score) {
        highscoreText.setText(String.valueOf(score));
    }

    @FXML
    private void switchToHighscore() throws IOException {
        App.setRoot("Highscore");
    }

    @Override
    public void setGameOver() {
        gameView.forceDrawFrame(canvas, game);
        gameOverPane.setVisible(true);
        gameOverScore.setText(scoreText.getText());
        gameOverHighscore.setText(highscoreText.getText());
        gameOverTimestamp = new Date();
    }

    @Override
    public void resetGameOver() {
        gameOverPane.setVisible(false);
    }

    private void restartGame() {
        gameView.resetView(canvas, game);
        game = new Game(this);
        setScoreText(game.getScore());
        resetGameOver();
    }

    private int getTimeBetweenGameOver() {
        if (gameOverTimestamp == null) {
            return 0;
        }
        Date now = new Date();
        return (int) (now.getTime() - gameOverTimestamp.getTime());
    }

    public void toggleSnake() {
        activatedAI = !activatedAI;
        gameView.resetView(canvas, game);
        if (activatedAI) {
            game = new GameAI(this);
            toggleSnakeButton.setText("Turn off AI");
            Constants.setFrameRate(Constants.FRAMERATE_AI);
        } else {
            game = new Game(this);
            toggleSnakeButton.setText("Snake AI");
            Constants.setFrameRate(Constants.FRAMERATE_HUMAN);
        }
        executor.scheduleAtFixedRate(frameUpdate, 0, (long) Constants.FRAMETIME.toMillis(), TimeUnit.MILLISECONDS);
        setScoreText(game.getScore());
        resetGameOver();
    }

}
