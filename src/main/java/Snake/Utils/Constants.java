package Snake.Utils;

import javafx.util.Duration;

public class Constants {

    public static final int frameRate = 10;
    public static final Duration frameTime = Duration.millis(1 / ((double) frameRate) * 1000);

    public static final int rowCount = 25;
    public static final int columnCount = 25;

    /*
     * public static enum CELL { EMPTY, SNAKE }
     */
}
