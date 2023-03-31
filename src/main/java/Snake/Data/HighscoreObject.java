package Snake.Data;

import java.time.LocalDateTime;

/**
 * Dataclass for the highscore object. This class implements the Comparable interface, so that we can sort the highscore list.
 * The data saved in the highscore text file is written with this class in mind.
 */
public class HighscoreObject implements Comparable<HighscoreObject> {

    private LocalDateTime time;
    private String playerType;
    private int score;

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
