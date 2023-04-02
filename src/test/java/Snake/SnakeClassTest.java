package Snake;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import Snake.Model.Snake;
import Snake.Utils.Constants;
import Snake.Utils.Coordinate;

public class SnakeClassTest {

    @Test
    @DisplayName("Teste konstruktør")
    public void testConstructor() {
        Snake snake = new Snake(0, 0);
        assertEquals(new Coordinate(0, 0), snake.getHead().getPos(), "Sjekke at hode er på riktig posisjon");
        assertEquals(1, snake.getLength(), "Sjekke at lengden er 1 ved initialisering");
        assertThrows(IllegalArgumentException.class, () -> {
            new Snake(-2, 5);
        }, "Sjekke at konstruktør kaster IllegalArgumentException ved ugyldig posisjon");
    }

    @Nested
    class SnakeGameTestDefaultConstructor {

        private Snake snake;

        @BeforeEach
        public void setUp() throws Exception {
            this.snake = new Snake(Constants.COLUMNCOUNT / 2, Constants.ROWCOUNT - 1);
        }

        @Test
        @DisplayName("Sjekke at direction er riktig")
        public void testDirection() {
            snake.addDirection(new int[] { 1, 0 });
            assertTrue(Arrays.equals(new int[] { 1, 0 }, snake.getDirectionPeek()));

            assertThrows(IllegalArgumentException.class, () -> {
                snake.addDirection(new int[] { 1, 0, 0 });
            }, "Sjekke at addDirection kaster IllegalArgumentException ved ugyldig retning");

            snake.addDirection(new int[] { -1, 0 });
            assertTrue(Arrays.equals(new int[] { 1, 0 }, snake.getDirectionPeek())); // We are peeking, and not removing/executing the direction
            assertTrue(Arrays.equals(new int[] { 1, 0 }, snake.getDirection())); // Here we are getting the direction and removing it from the queue
            assertTrue(Arrays.equals(new int[] { -1, 0 }, snake.getDirection())); // This should return the direction we added last
        }

        @Test
        @DisplayName("Sjekke at snaken ikke kan bevege seg utenfor banen")
        public void testNextMoveValid() {
            snake.addDirection(new int[] { 1, 0 });
            assertTrue(snake.nextMoveValid());

            snake.move(false);
            snake.addDirection(new int[] { 0, 1 });
            assertFalse(snake.nextMoveValid());
            assertThrows(IllegalStateException.class, () -> snake.move(false),
                    "Sjekke at move kaster IllegalStateException når neste trekk er ugyldig");
        }

        @Test
        @DisplayName("Sjekke at snaken beveger seg i riktig retning")
        public void testSnakeIsGrowingOnEating() {
            snake.addDirection(new int[] { 1, 0 });
            snake.move(true);

            assertEquals(2, snake.getLength());
            snake.move(true);
            snake.move(true);
            assertEquals(4, snake.getLength());
        }

    }

}
