package Snake;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Snake.Data.Highscore;

public class FileHandlingTest {

    @Test
    @DisplayName("Test existing highscore")
    public void testHighscoreExistingFile() {
        Highscore highscore = new Highscore("highscore.txt");
        assertTrue(highscore.getFile().exists(), "Existing highscore file should exist");
    }

    @Test
    @DisplayName("Test non-existing highscore")
    public void testHighscoreNonExistingFile() {
        Highscore highscore = new Highscore("highscore1.txt");

        assertFalse(highscore.getFile().exists(), "Non-existing highscore file should not exist");
        assertTrue(highscore.readAllScores() == null, "Non-existing highscore file has no content");
        assertEquals(0, highscore.getHighScore(), "Non-existing highscore should return 0 as highscore");

        highscore.addScore(5, "Human");
        assertEquals(5, highscore.getHighScore(), "Highscore should return 5 after adding it");
        assertTrue(highscore.readAllScores() != null, "Text file now has content");

        highscore.resetAllScores();
        assertTrue(highscore.readAllScores().size() == 0,
                "After removing content, text file should be empty again (but should exist)");

        highscore.getFile().delete();
    }

}
