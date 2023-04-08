package Snake.Model.AI;

import java.util.List;
import java.util.stream.Collectors;

import Snake.Controller.ControllerListener;
import Snake.Model.Game;

/**
 * Game handler for the Snake AI. This class extends the Game class, and overrides update 
 * method by adding AI logic.
 */
public class GameAI extends Game {

    /** The number of moves the AI should look ahead */
    private int movesDepth = 5;

    public GameAI() {
        super(true);
    }

    public GameAI(ControllerListener controller) {
        super(controller, true);
    }

    @Override
    public void update() {
        if (isGameOver() || getPaused())
            return;
        if (getSnake().getDirectionSize() == 0) {
            long startTime = System.nanoTime();
            List<DirectionData> bestMove = SnakeAI.AINextMove(getSnake(), movesDepth, movesDepth, getFood(), null);
            long endTime = System.nanoTime();
            System.out.println("Time taken: " + (endTime - startTime) / 1000000d + "ms");
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
