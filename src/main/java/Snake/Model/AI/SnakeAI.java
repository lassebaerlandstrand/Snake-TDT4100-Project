package Snake.Model.AI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import Snake.Model.Snake;
import Snake.Utils.Constants;
import Snake.Utils.Coordinate;

/**
 * This is a simple AI, which does not use advanced AI techniques 
 * like neural networks or Q-learning (Reinforcement learning). 
 * This does not use perfect snake algorithms, e.g. Hamiltonian Cycle.
 * This goal of creating this is not to create an AI which solves snake perfectly, 
 * but rather simply exploring a simple AI model, which can be created in a short time.
 * This AI works by looking certain steps ahead, and then choosing the best move.
 */
public final class SnakeAI extends Snake {

    private static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors() / 2;

    private SnakeAI() {
        super(null, true);
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

    public static List<DirectionData> AINextMove(Snake currentSnake, int depth, int initialDepth,
            Coordinate food, List<DirectionData> previousDirections) {
        List<List<DirectionData>> directions = new ArrayList<>(AINextMoveRecursion(currentSnake, depth, food,
                previousDirections));

        List<DirectionData> bestDirection = null;
        for (List<DirectionData> direction : directions) {
            if (bestDirection == null) {
                bestDirection = new ArrayList<>(direction);
                continue;
            }

            DirectionData lastDirection = direction.get(direction.size() - 1);
            DirectionData lastBestDirection = bestDirection.get(bestDirection.size() - 1);

            // Checks if new direction has more available area
            if (lastDirection.getAvailableArea() + (hasEatenFood(direction) ? 2 : 0) > lastBestDirection
                    .getAvailableArea() + (hasEatenFood(bestDirection) ? 2 : 0)) {
                bestDirection = new ArrayList<>(direction);
                continue;
            }

            // Checks if new direction has equal area, but less distance
            // If equal distance, then chooses the direction with less moves
            if (lastDirection.getAvailableArea() + (hasEatenFood(direction) ? 2 : 0) == lastBestDirection
                    .getAvailableArea() + (hasEatenFood(bestDirection) ? 2 : 0)) {
                if (lastDirection.getDistance() < lastBestDirection.getDistance()) {
                    bestDirection = new ArrayList<>(direction);
                    continue;
                } else if (lastDirection.getDistance() == lastBestDirection.getDistance()
                        && direction.size() < bestDirection.size()) {
                    bestDirection = new ArrayList<>(direction);
                    continue;
                }
            }

            // Checks if new direction has more or equal area, and less distance
            if (lastDirection.getAvailableArea() >= lastBestDirection.getAvailableArea()) {
                if (lastDirection.getDistance() < lastBestDirection.getDistance()) {
                    bestDirection = new ArrayList<>(direction);
                }
            }
        }
        return bestDirection;
    }

    public static List<List<DirectionData>> AINextMoveRecursion(Snake currentSnake, int depth,
            Coordinate food, List<DirectionData> previousDirections) {
        List<List<DirectionData>> directions = new ArrayList<>();

        // Testing resulted in creating executor service here to be faster than creating in AINextMove
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE); // Possibly use cached thread pool
        List<Future<List<List<DirectionData>>>> futureResults = new ArrayList<>();

        List<int[]> validDirections = SnakeAI.getValidDirections(currentSnake);
        for (int[] direction : validDirections) {
            List<DirectionData> previousDirectionsCopy = previousDirections == null ? new ArrayList<>()
                    : new ArrayList<>(previousDirections);
            Snake snakeClone = new Snake(currentSnake, true);
            snakeClone.addDirection(direction);
            boolean ateFood = snakeClone.nextPositionPeek().equals(food);
            snakeClone.move(ateFood);
            int distance = Coordinate.distanceFast(snakeClone.getHead().getPos(), food);

            if (depth == 1 || ateFood) {
                previousDirectionsCopy
                        .add(new DirectionData(direction, availableArea(snakeClone), distance, ateFood));
                directions.add(new ArrayList<>(previousDirectionsCopy));
            } else {
                previousDirectionsCopy
                        .add(new DirectionData(direction, 0, distance, ateFood));
                Callable<List<List<DirectionData>>> callable = () -> AINextMoveRecursion(snakeClone, depth - 1,
                        food,
                        previousDirectionsCopy);
                futureResults.add(executor.submit(callable));
            }
        }

        executor.shutdown();
        for (Future<List<List<DirectionData>>> future : futureResults) {
            try {
                List<List<DirectionData>> result = future.get();
                directions.addAll(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return directions;
    }

    private static boolean hasEatenFood(List<DirectionData> directions) {
        return directions.stream().anyMatch(direction -> direction.getFoodEaten());
    }

    /**
     * Uses floodfill algorithm to detect the amount of available area for the snake to move to
     * @param snake The snake to detect the available area for
     * @return The amount of available area for the snake to move to
     */
    private static int availableArea(Snake snake) {
        // Now we can use the floodfill algorithm to detect which way the snake should move to maximize the available area
        return floodFill(snake, snake.getHead().getPos(), new boolean[Constants.COLUMNCOUNT][Constants.ROWCOUNT]);
    }

    /** 
     * Checks whether a cell is within grid and not visited or part of the snake
     * @param visited The grid of visited cells
     * @param position The position to check
     * @param snake The snake to check if position is a part of
     * @return Whether the cell is valid
     */
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

    /** Floodfill algorithm, returns size of visited area */
    public static int floodFill(Snake snake, Coordinate position, boolean[][] visited) {

        List<Integer> transitionX = new ArrayList<>(Arrays.asList(1, -1, 0, 0));
        List<Integer> transitionY = new ArrayList<>(Arrays.asList(0, 0, 1, -1));

        Queue<Coordinate> queue = new PriorityQueue<Coordinate>();
        queue.add(position);
        visited[position.getX()][position.getY()] = true;
        int count = 1;

        while (!queue.isEmpty()) {
            Coordinate currentPos = queue.poll();
            for (int i = 0; i < 4; i++) {
                Coordinate newPos = new Coordinate(currentPos.getX() + transitionX.get(i),
                        currentPos.getY() + transitionY.get(i));
                if (isValidCell(visited, newPos, snake)) {
                    visited[newPos.getX()][newPos.getY()] = true;
                    queue.add(newPos);
                    count++;
                }
            }
        }

        return count;
    }
}
