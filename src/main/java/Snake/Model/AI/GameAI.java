package Snake.Model.AI;

import java.util.List;
import java.util.stream.Collectors;

import Snake.Controller.ControllerListener;
import Snake.Model.Game;

public class GameAI extends Game {

    private int movesAhead = 2;

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
            if (bestMove != null) {
                getSnake().addDirection(
                        bestMove.stream().map(direction -> direction.getDirection()).collect(Collectors.toList()));
            } else {
                getSnake().addDirection(Direction.getRandomDirection());
            }
        }
        super.update();
    }
}
