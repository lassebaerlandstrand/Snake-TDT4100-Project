package Snake.Controller;

public interface ControllerListener {

    public void setScoreText(int score);

    public void setHighscoreText(int score);

    public void setGameOver();

    public void resetGameOver();
}
