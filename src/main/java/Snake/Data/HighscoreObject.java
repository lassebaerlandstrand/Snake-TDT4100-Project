package Snake.Data;

import java.time.LocalDateTime;

public class HighscoreObject {

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

}
