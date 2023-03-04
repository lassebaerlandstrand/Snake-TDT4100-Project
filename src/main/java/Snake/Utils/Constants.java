package Snake.Utils;

import javafx.util.Duration;

public class Constants {

    public static final int FRAMERATE = 10;
    public static final Duration FRAMETIME = Duration.millis(1 / ((double) FRAMERATE) * 1000);

    public static final int ROWCOUNT = 25;
    public static final int COLUMNCOUNT = 25;

    /*
     * public static enum CELL { EMPTY, SNAKE }
     */
}
