package Snake.Data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

// Singleton class
public class Highscore {

    private File file;

    public Highscore(String fileName) {
        try {
            file = new File(getClass().getResource(fileName).toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public String readAllScores() {
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            StringBuffer sb = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            fileReader.close();
            return sb.toString();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
        return null;
    }

    public void addScore(int score) {

    }

    public void resetAllScores() {

    }

    public int getHighScore() {
        String allScores = readAllScores();
        if (allScores == null) {
            return 0;
        }
        String[] scores = allScores.split("\n");
        int highScore = 0;
        for (String score : scores) {
            int scoreInt = Integer.parseInt(score);
            if (scoreInt > highScore) {
                highScore = scoreInt;
            }
        }
        return highScore;
    }
}
