package Snake.Controller;

/**
 * Interface for the controller. This is used to communicate between the model and the controller.
 * With this interface, we get an observable-observer pattern, where the model is the observable, and the controller is the observer.
 */
public interface ControllerListener {

    public void setScoreText(int score);

    public void setHighscoreText(int score);

    public void setGameOver();

    public void resetGameOver();
}
