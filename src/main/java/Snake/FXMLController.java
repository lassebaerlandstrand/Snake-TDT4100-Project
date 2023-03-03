package Snake;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class FXMLController {

    @FXML
    private Canvas canvas;

    private Timeline timeline;

    @FXML
    protected void initialize() {
        draw();
    }

    @FXML
    private void keyListener(ActionEvent event) {
        System.out.println("Hei");
    }

    @FXML
    private void abc() {
        System.out.println("A");
    }

    // Redraw entire canvas
    @FXML
    public void draw() {
        timeline = new Timeline(new KeyFrame(Constants.frameTime, (ActionEvent event) -> {
            GraphicsContext context = canvas.getGraphicsContext2D();
            context.setFill(Color.RED);
            context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            System.out.println(canvas.getHeight());
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
