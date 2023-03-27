package Snake.View;

import java.util.ArrayList;
import java.util.List;

import Snake.Model.Coordinate;
import Snake.Model.Food;
import Snake.Model.Game;
import Snake.Model.Snake;
import Snake.Model.SnakeCell;
import Snake.Utils.Constants;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

// View
public class GameView {

    private double lineWidth = 0d; // Lines between grid cells
    private double cellBorderWidth = 1d; // Border around grid cells
    private int borderWidth = (800 - (Constants.COLUMNCOUNT * Constants.CELLSIZE)) / 2;
    private Color backgroundColorLight = Color.web("#acd855");
    private Color backgroundColorDark = Color.web("#a2d149");
    private Color borderColor = Color.web("#578a34");

    // Used for optimizing draw function, to only draw the changed cells, not redraw the whole grid
    private Snake previousDrawnSnake;
    private Food previousApple;

    public void drawBackground(Canvas canvas, Game game) {
        GraphicsContext context = canvas.getGraphicsContext2D();

        // Border
        context.setFill(borderColor);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Background
        // Draw large rectangle with one color, then fill in the small rectangles with the other color
        context.setFill(backgroundColorLight);
        context.fillRect(borderWidth, borderWidth, canvas.getWidth() - borderWidth * 2,
                canvas.getHeight() - borderWidth * 2);

        context.setFill(backgroundColorDark);
        for (int i = 0; i < Constants.COLUMNCOUNT * Constants.ROWCOUNT; i += 2) {
            context.fillRect(
                    borderWidth + (i % Constants.COLUMNCOUNT
                            + ((Constants.ROWCOUNT + 1) % 2 * ((i / Constants.ROWCOUNT + 1) % 2)))
                            * Constants.CELLSIZE,
                    borderWidth + (i / Constants.ROWCOUNT) * Constants.CELLSIZE,
                    Constants.CELLSIZE, Constants.CELLSIZE);
        }
    }

    private List<Coordinate> unusedCells(Snake previousDrawnSnake, Snake currentSnake) {
        List<Coordinate> previousCells = new ArrayList<>(previousDrawnSnake.getSnakeCoordinates());
        List<Coordinate> currentCells = currentSnake.getSnakeCoordinates();
        previousCells.removeAll(currentCells);
        return previousCells;
    }

    private List<SnakeCell> newCells(Snake previousDrawnSnake, Snake currentSnake) {
        List<SnakeCell> previousCells = new ArrayList<>(previousDrawnSnake.getSnakeCells());
        List<SnakeCell> currentCells = new ArrayList<>(currentSnake.getSnakeCells());
        currentCells.removeAll(previousCells);
        return currentCells;
    }

    private void drawCell(GraphicsContext context, double x, double y, Color color) {
        if (context.getFill() != color)
            context.setFill(color);

        context.fillRect(x * Constants.CELLSIZE + borderWidth,
                (Constants.ROWCOUNT - 1 - y) * Constants.CELLSIZE + borderWidth,
                Constants.CELLSIZE - lineWidth,
                Constants.CELLSIZE - lineWidth);
        context.strokeRect(x * Constants.CELLSIZE + borderWidth + cellBorderWidth / 2,
                (Constants.ROWCOUNT - 1 - y) * Constants.CELLSIZE + borderWidth
                        + cellBorderWidth / 2,
                Constants.CELLSIZE - lineWidth - cellBorderWidth,
                Constants.CELLSIZE - lineWidth - cellBorderWidth);
    }

    private void drawGame(GraphicsContext context, Canvas canvas, Game game) {

        // Reset unused cells (where snake previously was)
        if (previousDrawnSnake != null) {
            for (Coordinate coordinate : unusedCells(previousDrawnSnake, game.getSnake())) {
                context.setFill(
                        (coordinate.getX() + coordinate.getY()) % 2 == 0 ? backgroundColorDark : backgroundColorLight);
                context.fillRect(coordinate.getX() * Constants.CELLSIZE + borderWidth,
                        (Constants.ROWCOUNT - 1 - coordinate.getY()) * Constants.CELLSIZE + borderWidth,
                        Constants.CELLSIZE, Constants.CELLSIZE);
            }
        }

        // Snake
        List<SnakeCell> snakeCells = game.getSnake().getSnakeCells();
        if (previousDrawnSnake != null)
            snakeCells = newCells(previousDrawnSnake, game.getSnake()); // Only update cells which have changed

        for (SnakeCell snakeCell : snakeCells) {
            drawCell(context, snakeCell.getX(), snakeCell.getY(), snakeCell.getColor());
        }

        // Apple
        if (!game.getFood().equals(previousApple)) {
            drawCell(context, game.getFood().getX(), game.getFood().getY(), game.getFood().getColor());
        }

        previousDrawnSnake = new Snake(game.getSnake());
        previousApple = new Food(game.getFood().getPos());
    }

    public void drawFrame(Canvas canvas, Game game) {
        if (!game.getGameOver()) {
            forceDrawFrame(canvas, game);
        }
    }

    // One use case is to draw after game has ended, this is done once
    public void forceDrawFrame(Canvas canvas, Game game) {
        GraphicsContext context = canvas.getGraphicsContext2D();
        context.setStroke(Color.WHITE);
        context.setLineWidth(cellBorderWidth);
        drawGame(context, canvas, game);
    }

    public void drawInitial(Canvas canvas, Game game) {
        drawBackground(canvas, game);
        drawFrame(canvas, game);
    }

    // Draw over the previous game's snake and apple
    public void resetView(Canvas canvas, Game game) {
        GraphicsContext context = canvas.getGraphicsContext2D();

        // Snake
        List<SnakeCell> snakeCells = game.getSnake().getSnakeCells();
        for (SnakeCell snakeCell : snakeCells) {
            context.setFill(
                    (snakeCell.getX() + snakeCell.getY()) % 2 == 0 ? backgroundColorDark : backgroundColorLight);
            context.fillRect(snakeCell.getX() * Constants.CELLSIZE + borderWidth,
                    (Constants.ROWCOUNT - 1 - snakeCell.getY()) * Constants.CELLSIZE + borderWidth,
                    Constants.CELLSIZE, Constants.CELLSIZE);
        }

        // Apple
        context.setFill(
                (game.getFood().getX() + game.getFood().getY()) % 2 == 0 ? backgroundColorDark : backgroundColorLight);
        context.fillRect(game.getFood().getX() * Constants.CELLSIZE + borderWidth,
                (Constants.ROWCOUNT - 1 - game.getFood().getY()) * Constants.CELLSIZE + borderWidth,
                Constants.CELLSIZE, Constants.CELLSIZE);

        previousDrawnSnake = null;
        previousApple = null;
    }
}
