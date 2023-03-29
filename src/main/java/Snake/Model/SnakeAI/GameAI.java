package Snake.Model.SnakeAI;

import Snake.Controller.ControllerListener;
import Snake.Model.Game;

public class GameAI extends Game {

    private int movesAhead = 5;

    public GameAI() {
        super();
    }

    public GameAI(ControllerListener controller) {
        super(controller);
    }

    @Override
    public void update() {
        int[] bestMove = SnakeAI.AINextMove(getSnake(), movesAhead, getFood());
        getSnake().addDirection(bestMove);
        super.update();
    }

    public static void main(String[] args) {
        GameAI game = new GameAI();
        game.update();
    }

}
