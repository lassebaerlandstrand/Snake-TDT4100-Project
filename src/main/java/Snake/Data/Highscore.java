package Snake.Data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * File handler class for the highscore file
 */
public class HighScore {

    private File file;
    private int highScore; // Cache values, so we don't have to read from file every time

    /**
     * <p>Creates a new Highscore object with the given file name</p>
     * <p>If the file does exist, information will be appended at the end</p>
     * <p>If the file does not exist, it will be created</p>
     * @param fileName The name of the file in ''../resources/Snake/Data' to read from
     */
    public HighScore(String fileName) {
        Path projectPath = Paths.get("").toAbsolutePath();
        String dataPath = "/src/main/resources/Snake/Data";
        file = new File(projectPath.toAbsolutePath().toString() + dataPath + "/" + fileName);
        highScore = getHighScoreReadFile();
    }

    public File getFile() {
        return file;
    }

    /**
     * Reads all scores from the file associated with this object
     * @return A list of all {@link HighScoreObject HighscoreObjects} in the file associated with this object
     */
    public List<HighScoreObject> readAllScores() {
        try (FileReader fileReader = new FileReader(file)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<HighScoreObject> highscoreObjects = new ArrayList<HighScoreObject>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                LocalDateTime time = LocalDateTime.parse(line.split(",")[0]);
                String playerType = line.split(",")[1];
                int score = Integer.parseInt(line.split(",")[2]);
                highscoreObjects.add(new HighScoreObject(time, playerType, score));
            }
            return highscoreObjects;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
        return null;
    }

    /**
     * Append a new score to the file associated with this object
     * @param score The score to append
     * @param playerType The type of player that got the score (e.g. Human or AI)
     */
    public void addScore(int score, String playerType) {
        try (FileWriter fileWriter = new FileWriter(file, true)) { // Second parameter is true to append to file
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            HighScoreObject highscoreObject = new HighScoreObject(LocalDateTime.now(), playerType, score);
            bufferedWriter.write(
                    highscoreObject.getTime() + "," + playerType + "," + highscoreObject.getScore() + "," + "\n");
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error reading file");
        }

    }

    /**
     * Removes all content in the file associated with this object
     */
    public void resetAllScores() {
        try {
            new PrintWriter(file).close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    /**
     * Get the highscore stored in the object, if you are looking for the highest score in the file, use {@link HighScore#getHighScoreReadFile getHighScoreReadFile}
     * @return The highscore stored in the object (not necessarily the highest score in the file)
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * Reads the file and returns the highest score in the file associated with this object
     * @return The highest score in the file associated with this object
     */
    public int getHighScoreReadFile() {
        List<HighScoreObject> allScores = readAllScores();
        if (allScores == null) {
            return 0;
        }
        int highscore = 0;
        for (HighScoreObject score : allScores) {
            int scoreInt = score.getScore();
            if (scoreInt > highscore) {
                highscore = scoreInt;
            }
        }
        this.highScore = highscore;
        return highscore;
    }
}
