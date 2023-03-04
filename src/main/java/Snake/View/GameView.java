package Snake.View;

import Snake.Model.Game;
import Snake.Model.SnakeCell;
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
    private double lineWidth = 0d;
    private int borderWidth = 25;
    private Color backgroundColorLight = Color.web("#acd855");
    private Color backgroundColorDark = Color.web("#a2d149");
    private Color borderColor = Color.web("#578a34");

    private void drawGameOver() {

    }

    private void drawGame(GraphicsContext context, Canvas canvas, Game game) {

        double gameSizeX = canvas.getWidth() - 2 * borderWidth;
        double gameSizeY = canvas.getHeight() - 2 * borderWidth;

        double cellSizeX = gameSizeX / Constants.COLUMNCOUNT;
        double cellSizeY = gameSizeY / Constants.ROWCOUNT;
        double cellSize = Math.min(cellSizeX, cellSizeY);

        // Border
        context.setFill(borderColor);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Background
        for (int i = 0; i < Constants.COLUMNCOUNT; i++) {
            for (int j = 0; j < Constants.ROWCOUNT; j++) {
                if ((i + j) % 2 == 0) {
                    context.setFill(backgroundColorLight);
                } else {
                    context.setFill(backgroundColorDark);
                }
                context.fillRect(i * cellSize + borderWidth, j * cellSize + borderWidth, cellSize,
                        cellSize);
            }
        }

        // Snake
        context.setStroke(Color.WHITE);
        for (SnakeCell snakeCell : game.getSnake().getSnakeCells()) {
            context.setFill(snakeCell.getColor());
            context.fillRect(snakeCell.getX() * cellSize + borderWidth,
                    (Constants.ROWCOUNT - 1 - snakeCell.getY()) * cellSize + borderWidth,
                    cellSize - lineWidth,
                    cellSize - lineWidth);
            context.strokeRect(snakeCell.getX() * cellSize + borderWidth,
                    (Constants.ROWCOUNT - 1 - snakeCell.getY()) * cellSize + borderWidth,
                    cellSize - lineWidth,
                    cellSize - lineWidth);
        }

        // Apple

    }

    public void drawFrame(Canvas canvas, Game game) {
        GraphicsContext context = canvas.getGraphicsContext2D();
        drawGame(context, canvas, game);

        if (game.getGameOver()) {
            drawGameOver();
        }
    }

    // Redraw entire canvas
    public void drawCoroutine(Canvas canvas, Game game) {

        timeline = new Timeline(new KeyFrame(Constants.FRAMETIME, (ActionEvent event) -> {
            GraphicsContext context = canvas.getGraphicsContext2D();
            drawGame(context, canvas, game);
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
