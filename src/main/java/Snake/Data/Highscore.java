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

public class Highscore {

    private File file;
    private int highscore; // Cache values, so we don't have to read from file every time

    public Highscore(String fileName) {
        Path projectPath = Paths.get("").toAbsolutePath();
        String dataPath = "/src/main/resources/Snake/Data";
        file = new File(projectPath.toAbsolutePath().toString() + dataPath + "/" + fileName);
        highscore = getHighScoreReadFile();
    }

    public File getFile() {
        return file;
    }

    public List<HighscoreObject> readAllScores() {
        try (FileReader fileReader = new FileReader(file)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<HighscoreObject> highscoreObjects = new ArrayList<HighscoreObject>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                LocalDateTime time = LocalDateTime.parse(line.split(",")[0]);
                String playerType = line.split(",")[1];
                int score = Integer.parseInt(line.split(",")[2]);
                highscoreObjects.add(new HighscoreObject(time, playerType, score));
            }
            return highscoreObjects;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
        return null;
    }

    public void addScore(int score, String playerType) {
        try (FileWriter fileWriter = new FileWriter(file, true)) { // Second parameter is true to append to file
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            HighscoreObject highscoreObject = new HighscoreObject(LocalDateTime.now(), playerType, score);
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

    public void resetAllScores() {
        try {
            new PrintWriter(file).close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    public int getHighScore() {
        return highscore;
    }

    public int getHighScoreReadFile() {
        List<HighscoreObject> allScores = readAllScores();
        if (allScores == null) {
            return 0;
        }
        int highScore = 0;
        for (HighscoreObject score : allScores) {
            int scoreInt = score.getScore();
            if (scoreInt > highScore) {
                highScore = scoreInt;
            }
        }
        return highScore;
    }
}
