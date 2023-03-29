package Snake.Model.SnakeAI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import Snake.Model.Coordinate;
import Snake.Model.Snake;
import Snake.Utils.Constants;

/*
 * This is a simple AI, which does not use advanced AI techniques 
 * like neural networks or Q-learning (Reinforcement learning). 
 * This does not use perfect snake algorithms, e.g. Hamiltonian Cycle.
 * This goal of creating this is not to create an AI which solves snake perfectly, 
 * but rather simply exploring a simple AI model, which can be created in a short time. 
 */
// TODO: Caching values
// TODO: Leave an escape route, this can be done by checking next move's flood fill (but possibly inefficient), still it can handle it with no issues
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
        List<Direction> directions = new ArrayList<>();

        List<int[]> validDirections = SnakeAI.getValidDirections(currentSnake);
        for (int[] direction : validDirections) {
            Snake snakeClone = new Snake(currentSnake);
            snakeClone.addDirection(direction);
            boolean ateFood = snakeClone.nextPositionPeek().equals(food);
            snakeClone.move(ateFood);
            int distance = Coordinate.distanceFast(snakeClone.getHead().getPos(), food);
            directions.add(new Direction(direction,
                    currentSnake.getLength() == 1 ? Integer.MAX_VALUE : SnakeAI.wiseMove(snakeClone), distance,
                    ateFood));
        }

        // TODO: Bug when snake spawn directly besides apple
        Direction bestDirection = new Direction(null, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
        for (Direction direction : directions) {
            if (direction.getFoodEaten() && direction.getAvailableArea() > currentSnake.getLength() + 1) {
                bestDirection = direction;
            } else if (direction.getAvailableArea() + (direction.getFoodEaten() ? 2 : 0) > bestDirection
                    .getAvailableArea() + (bestDirection.getFoodEaten() ? 2 : 0)) {
                bestDirection = direction;
            } else if (direction.getAvailableArea() == bestDirection.getAvailableArea()) {
                if (direction.getDistance() < bestDirection.getDistance()) {
                    bestDirection = direction;
                }
            }
        }

        return bestDirection.getDirection();
    }

    // This method checks if the snake is trapped, and if so, it tries to find the best direction to move in
    // Based on the available area for the snake to move in
    private static int wiseMove(Snake snake) {
        // if (snake.getLength() < 7) {
        //     return Integer.MAX_VALUE;
        // }

        // This does not detect when the snake is in a cycle, but rather when the moment the snake gets trapped
        // List<SnakeCell> snakeCells = snake.getSnakeCells();
        // SnakeCell afterHeadCell = snakeCells.get(1);
        // SnakeCell tail = snakeCells.get(snake.getLength() - 1);

        // If afterHeadCell is a neighbour of the tail, and the head points towards the rest of the body, then thats the moment the snake gets trapped
        // if (Coordinate.distanceFast(afterHeadCell.getPos(), tail.getPos()) == 1) {
        // Now we can use the floodfill algorithm to detect which way the snake should move to maximaze the available area
        return floodFill(snake, snake.getHead().getPos(), new boolean[Constants.COLUMNCOUNT][Constants.ROWCOUNT]);
        // }
        // return Integer.MAX_VALUE;
    }

    private static boolean isValidCell(boolean[][] visited, Coordinate position, Snake snake) {
        int columns = visited.length;
        int rows = visited[0].length;
        if (position.getX() < 0 || position.getX() >= columns || position.getY() < 0 || position.getY() >= rows) {
            return false;
        }
        if (visited[position.getX()][position.getY()] || snake.getSnakeCoordinates().contains(position)) {
            return false;
        }
        return true;
    }

    // Floodfill algorithm, returns size of visited area
    public static int floodFill(Snake snake, Coordinate position, boolean[][] visited) {
        int columns = visited.length;
        int rows = visited[0].length;

        List<Integer> transitionX = new ArrayList<>(Arrays.asList(1, -1, 0, 0));
        List<Integer> transitionY = new ArrayList<>(Arrays.asList(0, 0, 1, -1));

        Queue<Coordinate> queue = new PriorityQueue<Coordinate>();
        queue.add(position);
        visited[position.getX()][position.getY()] = true;

        while (!queue.isEmpty()) {
            Coordinate currentPos = queue.poll();
            for (int i = 0; i < 4; i++) {
                Coordinate newPos = new Coordinate(currentPos.getX() + transitionX.get(i),
                        currentPos.getY() + transitionY.get(i));
                if (isValidCell(visited, newPos, snake)) {
                    visited[newPos.getX()][newPos.getY()] = true;
                    queue.add(newPos);
                }
            }
        }

        // Count how many cells are visited
        int count = 0;
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                if (visited[i][j]) {
                    count++;
                }
            }
        }

        return count;
    }
}
