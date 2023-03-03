package Snake;

import Snake.Utils.Constants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// View
public class GameView {

    private Timeline timeline;
    private double lineWidth;

    // Redraw entire canvas
    public void draw(Canvas canvas) {
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
