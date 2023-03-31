package Snake.Data;

import java.time.LocalDateTime;

public class HighscoreObject implements Comparable<HighscoreObject> {

    private LocalDateTime time;
    private String playerType;
    private int score;

    // public HighscoreObject(LocalDateTime time, int score) {
    //     this.time = time;
    //     this.score = score;
    //     playerType = "Human";
    // }

    public HighscoreObject(LocalDateTime time, String playerType, int score) {
        this.time = time;
        this.playerType = playerType;
        this.score = score;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public int getScore() {
        return score;
    }

    public String getPlayerType() {
        return playerType;
    }

    @Override
    public String toString() {
        return time + " " + score;
    }

    @Override
    public int compareTo(HighscoreObject arg0) {
        return arg0.getScore() - this.getScore();
    }

}
