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

    public Highscore(String fileName) {
        Path projectPath = Paths.get("").toAbsolutePath();
        String dataPath = "/src/main/java/Snake/Data";
        file = new File(projectPath.toAbsolutePath().toString() + dataPath + "/" + fileName);
        // try {
        //     //file = new File(getClass().getResource(fileName).toURI());
        //     file = new File(currentDir.toAbsolutePath().toString() + projectPath + "/" + fileName);
        // } 
    }

    public List<HighscoreObject> readAllScores() {
        try (FileReader fileReader = new FileReader(file)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            List<HighscoreObject> highscoreObjects = new ArrayList<HighscoreObject>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                LocalDateTime time = LocalDateTime.parse(line.split(",")[0]);
                int score = Integer.parseInt(line.split(",")[1]);
                highscoreObjects.add(new HighscoreObject(time, score));
            }
            return highscoreObjects;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
        return null;
    }

    public void addScore(int score) {
        try (FileWriter fileWriter = new FileWriter(file, true)) { // True = append
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            //bufferedWriter.write(score + "\n");
            HighscoreObject highscoreObject = new HighscoreObject(LocalDateTime.now(), score);
            // HashMap<String, Object> details = new HashMap<String, Object>();
            // details.put("score", highscoreObject.getScore());
            // details.put("time", highscoreObject.getDate().toString());
            // JSONObject jsonObject = new JSONObject(details);
            bufferedWriter.write(highscoreObject.getTime() + "," + highscoreObject.getScore() + "\n");
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
