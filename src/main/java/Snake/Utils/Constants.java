package Snake.Utils;

import javafx.util.Duration;

public class Constants {

    public static final int FRAMERATE_HUMAN = 12;
    public static final int FRAMERATE_AI = 60;

    public static int FRAMERATE = FRAMERATE_HUMAN;
    public static Duration FRAMETIME = Duration.millis(1 / ((double) FRAMERATE) * 1000);

    public static void setFrameRate(int frameRate) {
        FRAMERATE = frameRate;
        FRAMETIME = Duration.millis(1 / ((double) FRAMERATE) * 1000);
    }

    public static final int ROWCOUNT = 25;
    public static final int COLUMNCOUNT = 25;
    public static final int CELLSIZE = 28;

}
