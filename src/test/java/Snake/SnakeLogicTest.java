package Snake;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import Snake.Data.Highscore;
import Snake.Model.Game;

public class SnakeLogicTest {

    @Test
    @DisplayName("Teste konstruktør")
    public void testConstructor() {
        Game game = new Game();
        assertEquals(0, game.getScore(), "Sjekke at scoren er null ved initialisering");
        assertFalse(game.getGameOver(), "Sjekke at spillet ikke er over før det har startet");
        assertEquals(new Highscore("highscore.txt").getHighScore(), game.getHighScore());
        // assertThrows(IllegalArgumentException.class, () -> {
        //     new CoffeeCup(-2, 5);
        // });
    }

    @Nested
    class CoffeeCupTestDefaultConstructor {

        private Game game;

        @BeforeEach
        public void setUp() throws Exception {
            this.game = new Game();
        }

        @Test
        @DisplayName("Sjekke increaseCupSize")
        public void testScore() {
            game.increaseScore();
            assertEquals(1, game.getScore(), "Sjekke at scoren øker med 1");
            game.increaseScore();
            game.increaseScore();
            assertEquals(3, game.getScore(), "Sjekke at scoren stemmer overens");
        }

        // @Test
        // @DisplayName("Sjekke kaffedrikking")
        // public void fillCoffee() {
        //     this.coffeeCup.increaseCupSize(50);
        //     assertEquals(50, this.coffeeCup.getCapacity(), "Test");

        //     this.coffeeCup.fillCoffee(20);
        //     assertEquals(20, this.coffeeCup.getCurrentVolume(), "Test");
        //     this.coffeeCup.drinkCoffee(5);
        //     assertEquals(15, this.coffeeCup.getCurrentVolume(), "Test");
        //     assertThrows(IllegalArgumentException.class, () -> {
        //         this.coffeeCup.drinkCoffee(20);
        //     }, "Test");
        // }

    }

}
