package Snake.Model.AI;

import java.util.List;
import java.util.stream.Collectors;

import Snake.Controller.ControllerListener;
import Snake.Model.Game;

public class GameAI extends Game {

    private int movesAhead = 3;

    public GameAI() {
        super(true);
    }

    public GameAI(ControllerListener controller) {
        super(controller, true);
    }

    @Override
    public void update() {
        if (getGameOver() || getPaused())
            return;
        if (getSnake().getDirectionSize() == 0) {
            List<DirectionData> bestMove = SnakeAI.AINextMove(getSnake(), movesAhead, movesAhead, getFood(), null);
            if (bestMove != null) {
                getSnake().addDirection(
                        bestMove.stream().map(direction -> direction.getDirection()).collect(Collectors.toList()));
            } else {
                getSnake().addDirection(DirectionData.getRandomDirection());
            }
        }
        super.update();
    }
}
