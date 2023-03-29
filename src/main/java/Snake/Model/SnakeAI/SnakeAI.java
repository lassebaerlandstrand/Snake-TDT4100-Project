package Snake.Model.SnakeAI;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import Snake.Model.Coordinate;
import Snake.Model.Snake;

// TODO: Caching values
public final class SnakeAI extends Snake {

    private SnakeAI() {
        super(null);
    }

    private static int[] getInvalidDirection(Snake snake) {
        int[] direction = snake.getDirectionPeek();
        return snake.getLength() > 1 ? new int[] { -direction[0], -direction[1] } : null;
    }

    private static List<int[]> getValidDirections(Snake snake) {
        int[] invalidDirection = SnakeAI.getInvalidDirection(snake);
        return IntStream.range(0, 4).mapToObj(i -> new int[] { i % 2 == 0 ? i - 1 : 0, i % 2 == 0 ? 0 : i - 2 })
                .filter(direction -> !Arrays.equals(direction, invalidDirection) && snake.nextMoveValid(direction))
                .collect(Collectors.toList());
    }

    public static int[] AINextMove(Snake currentSnake, int movesAhead, Coordinate food) {
        int[] bestDirection = new int[] { 0, 1 };
        // int[] bestDirection = new int[] { 0, 0 };
        int minDistance = Integer.MAX_VALUE;

        List<int[]> validDirections = SnakeAI.getValidDirections(currentSnake);
        for (int[] direction : validDirections) {
            Snake snakeClone = new Snake(currentSnake);
            snakeClone.addDirection(direction);
            snakeClone.move(snakeClone.nextPositionPeek().equals(food));
            int distance = Coordinate.distanceFast(snakeClone.getHead().getPos(), food);
            if (distance < minDistance) {
                minDistance = distance;
                bestDirection = direction;
            }
        }
        return bestDirection;
    }
}
