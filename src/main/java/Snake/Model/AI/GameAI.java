package Snake.Model.AI;

import java.util.List;
import java.util.stream.Collectors;

import Snake.Controller.ControllerListener;
import Snake.Model.Game;

public class GameAI extends Game {

    private int movesAhead = 3;

    public GameAI() {
        super();
    }

    public GameAI(ControllerListener controller) {
        super(controller);
    }

    @Override
    public void update() {
        if (getGameOver() || getPaused())
            return;
        if (getSnake().getDirectionSize() == 0) {
            List<Direction> bestMove = SnakeAI.AINextMoveRecursion(getSnake(), movesAhead, getFood(), null);
            System.out.println(bestMove.size());
            getSnake().addDirection(
                    bestMove.stream().map(direction -> direction.getDirection()).collect(Collectors.toList()));
        }
        // getSnake().addDirection(bestMove);
        super.update();
    }

    // public static void main(String[] args) {
    //     GameAI game = new GameAI();
    //     // System.out.println(SnakeAI.floodFill(game.getSnake(), game.getSnake().getHead().getPos(), visited));

    //     // boolean[][] visited = new boolean[Constants.COLUMNCOUNT][Constants.ROWCOUNT];
    //     // for (int i = 0; i < Constants.COLUMNCOUNT; i++) {
    //     //     for (int j = 0; j < Constants.ROWCOUNT; j++) {
    //     //         visited[i][j] = false;
    //     //     }
    //     // }

    //     // Snake snake = new Snake(12, 12);
    //     // SnakeCell head = snake.getHead();

    //     // SnakeCell cell1 = new SnakeCell(head.getX(), head.getY() - 1, Color.RED);
    //     // SnakeCell cell2 = new SnakeCell(head.getX() + 1, head.getY() - 1, Color.RED);
    //     // SnakeCell cell3 = new SnakeCell(head.getX() + 2, head.getY() - 1, Color.RED);
    //     // SnakeCell cell4 = new SnakeCell(head.getX() + 2, head.getY(), Color.RED);
    //     // SnakeCell cell5 = new SnakeCell(head.getX() + 2, head.getY() + 1, Color.RED);
    //     // SnakeCell cell6 = new SnakeCell(head.getX() + 2, head.getY() + 2, Color.RED);
    //     // SnakeCell cell7 = new SnakeCell(head.getX() + 1, head.getY() + 2, Color.RED);
    //     // SnakeCell cell8 = new SnakeCell(head.getX(), head.getY() + 2, Color.RED);
    //     // SnakeCell cell9 = new SnakeCell(head.getX() - 1, head.getY() + 2, Color.RED);
    //     // SnakeCell cell10 = new SnakeCell(head.getX() - 1, head.getY() + 1, Color.RED);
    //     // SnakeCell cell11 = new SnakeCell(head.getX() - 1, head.getY(), Color.RED);
    //     // SnakeCell cell12 = new SnakeCell(head.getX() - 1, head.getY() - 1, Color.RED);

    //     // snake.appendSnakeCells(cell1);
    //     // snake.appendSnakeCells(cell2);
    //     // snake.appendSnakeCells(cell3);
    //     // snake.appendSnakeCells(cell4);
    //     // snake.appendSnakeCells(cell5);
    //     // snake.appendSnakeCells(cell6);
    //     // snake.appendSnakeCells(cell7);
    //     // snake.appendSnakeCells(cell8);
    //     // snake.appendSnakeCells(cell9);
    //     // snake.appendSnakeCells(cell10);
    //     // snake.appendSnakeCells(cell11);
    //     // snake.appendSnakeCells(cell12);

    //     // System.out.println(SnakeAI.floodFill(snake, head.getPos(), visited));
    // }

}
