package Snake;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;

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
        gameView.draw(canvas);
    }

    @FXML
    private void keyListener(ActionEvent event) {
        System.out.println("Hei");
    }

    @FXML
    private void abc() {
        System.out.println("A");
    }

    @FXML
    private void updateView() {
        gameView.draw(canvas);
    }

}
