package Snake.View;

import java.util.List;

import Snake.Model.Coordinate;
import Snake.Model.Game;
import Snake.Model.Snake;
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
    private double lineWidth = 0d; // Lines between grid cells
    private double cellBorderWidth = 1d; // Border around grid cells
    private int borderWidth = (800 - (Constants.COLUMNCOUNT * Constants.CELLSIZE)) / 2;
    private Color backgroundColorLight = Color.web("#acd855");
    private Color backgroundColorDark = Color.web("#a2d149");
    private Color borderColor = Color.web("#578a34");

    // Used for optimizing draw function, to only draw the changed cells, not redraw the whole grid
    private Snake previousDrawnSnake;

    private void drawGameOver() {

    }

    public void drawBackground(Canvas canvas, Game game) {
        GraphicsContext context = canvas.getGraphicsContext2D();

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
                context.fillRect(i * Constants.CELLSIZE + borderWidth,
                        (Constants.ROWCOUNT - 1 - j) * Constants.CELLSIZE + borderWidth,
                        Constants.CELLSIZE,
                        Constants.CELLSIZE);
            }
        }
    }

    private List<Coordinate> unusedCells(Snake previousDrawnSnake, Snake currentSnake) {

        List<Coordinate> previousCells = previousDrawnSnake.getSnakeCoordinates();
        List<Coordinate> currentCells = currentSnake.getSnakeCoordinates();
        // System.out.println("Previous cells: " + previousCells);
        // System.out.println("Current cells: " + currentCells);
        previousCells.removeAll(currentCells);
        return previousCells;
    }

    private boolean shouldDrawCellBorder() {
        return Math.max(Constants.COLUMNCOUNT, Constants.ROWCOUNT) < 100;
    }

    private void drawGame(GraphicsContext context, Canvas canvas, Game game) {

        // Reset unused cells (where snake previously was)
        if (previousDrawnSnake != null) {
            // System.out.println("Previous drawn snake: " + previousDrawnSnake);
            for (Coordinate coordinate : unusedCells(previousDrawnSnake, game.getSnake())) {
                if ((coordinate.getX() + coordinate.getY()) % 2 == 0) {
                    context.setFill(backgroundColorLight);
                } else {
                    context.setFill(backgroundColorDark);
                }
                context.fillRect(coordinate.getX() * Constants.CELLSIZE + borderWidth,
                        (Constants.ROWCOUNT - 1 - coordinate.getY()) * Constants.CELLSIZE + borderWidth,
                        Constants.CELLSIZE, Constants.CELLSIZE);
            }
        }

        // Snake
        context.setStroke(Color.WHITE);
        context.setLineWidth(cellBorderWidth);
        for (SnakeCell snakeCell : game.getSnake().getSnakeCells()) {
            context.setFill(snakeCell.getColor());
            context.fillRect(snakeCell.getX() * Constants.CELLSIZE + borderWidth,
                    (Constants.ROWCOUNT - 1 - snakeCell.getY()) * Constants.CELLSIZE + borderWidth,
                    Constants.CELLSIZE - lineWidth,
                    Constants.CELLSIZE - lineWidth);
            context.strokeRect(snakeCell.getX() * Constants.CELLSIZE + borderWidth + cellBorderWidth / 2,
                    (Constants.ROWCOUNT - 1 - snakeCell.getY()) * Constants.CELLSIZE + borderWidth
                            + cellBorderWidth / 2,
                    Constants.CELLSIZE - lineWidth - cellBorderWidth,
                    Constants.CELLSIZE - lineWidth - cellBorderWidth);
        }

        // Apple
        context.setFill(game.getFood().getColor());
        context.setLineWidth(cellBorderWidth);
        context.fillRect(game.getFood().getX() * Constants.CELLSIZE + borderWidth,
                (Constants.ROWCOUNT - 1 - game.getFood().getY()) * Constants.CELLSIZE + borderWidth,
                Constants.CELLSIZE - lineWidth,
                Constants.CELLSIZE - lineWidth);
        context.strokeRect(game.getFood().getX() * Constants.CELLSIZE + borderWidth + cellBorderWidth / 2,
                (Constants.ROWCOUNT - 1 - game.getFood().getY()) * Constants.CELLSIZE + borderWidth
                        + cellBorderWidth / 2,
                Constants.CELLSIZE - lineWidth - cellBorderWidth,
                Constants.CELLSIZE - lineWidth - cellBorderWidth);

        previousDrawnSnake = new Snake(game.getSnake());
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
