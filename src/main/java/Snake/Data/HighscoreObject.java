package Snake.Data;

import java.time.LocalDateTime;

public class HighscoreObject implements Comparable<HighscoreObject> {

    private LocalDateTime time;
    private int score;

    public HighscoreObject(LocalDateTime time, int score) {
        this.time = time;
        this.score = score;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public int getScore() {
        return score;
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
