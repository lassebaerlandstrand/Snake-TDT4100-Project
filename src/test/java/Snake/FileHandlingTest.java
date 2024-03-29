package Snake;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Snake.Data.HighScore;

public class FileHandlingTest {

    @Test
    @DisplayName("Test existing highscore")
    public void testHighscoreExistingFile() {
        HighScore highscore = new HighScore("highscore.txt");
        assertTrue(highscore.getFile().exists(), "Existing highscore file should exist");
    }

    @Test
    @DisplayName("Test non-existing highscore")
    public void testHighscoreNonExistingFile() {
        HighScore highscore = new HighScore("highscore1.txt");

        assertFalse(highscore.getFile().exists(), "Non-existing highscore file should not exist");
        assertTrue(highscore.readAllScores() == null, "Non-existing highscore file has no content");
        assertEquals(0, highscore.getHighScore(), "Non-existing highscore should return 0 as highscore");

        highscore.addScore(5, "Human");
        assertEquals(0, highscore.getHighScore(),
                "This method should not read from file, only use the previously stored value");
        assertEquals(5, highscore.getHighScoreReadFile(), "Highscore should return 5 after adding it");
        assertEquals(5, highscore.getHighScore(), "Highscore is now updated and should return the actual highscore");
        assertTrue(highscore.readAllScores() != null, "Text file now has content");

        highscore.resetAllScores();
        assertTrue(highscore.readAllScores().size() == 0,
                "After removing content, text file should be empty again (but should exist)");

        highscore.getFile().delete();
    }

}
