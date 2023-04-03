package Snake;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import Snake.Data.HighScore;
import Snake.Model.Game;
import Snake.Utils.Constants;
import Snake.Utils.Coordinate;

public class SnakeGameLogicTest {

    @Test
    @DisplayName("Teste konstruktør")
    public void testConstructor() {
        Game game = new Game();
        assertEquals(0, game.getScore(), "Sjekke at scoren er null ved initialisering");
        assertFalse(game.isGameOver(), "Sjekke at spillet ikke er over før det har startet");
        assertEquals(new HighScore("highscore.txt").getHighScore(), game.getHighScore());

        // Check if available positions match
        List<Coordinate> availablePositions = game.getAvailablePositions();
        Coordinate startingPosition = game.getSnake().getHead().getPos();
        List<Coordinate> allPositions = IntStream.range(0, Constants.COLUMNCOUNT)
                .mapToObj(x -> IntStream.range(0, Constants.ROWCOUNT).mapToObj(y -> new Coordinate(x, y)))
                .flatMap(x -> x).collect(Collectors.toList());
        allPositions.remove(startingPosition);
        assertTrue(availablePositions.equals(allPositions));
    }

    @Nested
    class SnakeGameTestDefaultConstructor {

        private Game game;

        @BeforeEach
        public void setUp() throws Exception {
            this.game = new Game();
        }

        @Test
        @DisplayName("Sjekke at score-telling fungerer")
        public void testScore() {
            game.increaseScore();
            assertEquals(1, game.getScore(), "Sjekke at scoren øker med 1");
            game.increaseScore();
            game.increaseScore();
            assertEquals(3, game.getScore(), "Sjekke at scoren stemmer overens");
        }

        @Test
        @DisplayName("Sjekke at snaken beveger seg i riktig retning")
        public void testSnakeMovement() {
            Coordinate startPos = game.getSnake().getHead().getPos();
            int newXDirection = 1;
            if (startPos.getX() + 1 >= Constants.COLUMNCOUNT)
                newXDirection = -1;
            game.getSnake().addDirection(new int[] { newXDirection, 0 });
            game.update();

            Coordinate nextPos = game.getSnake().getHead().getPos();
            assertEquals(new Coordinate(startPos.getX() + newXDirection, startPos.getY()), nextPos,
                    "Slangen bevegde seg ikke i riktig retning");
        }

    }

}
