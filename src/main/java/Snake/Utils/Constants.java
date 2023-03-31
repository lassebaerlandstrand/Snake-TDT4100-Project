package Snake.Utils;

import javafx.util.Duration;

public class Constants {

    public static final int FRAMERATE_HUMAN = 12;
    public static final int FRAMERATE_AI = 60;

    /** Framerate which the game's update time is based on */
    public static int FRAMERATE = FRAMERATE_HUMAN;
    /** Frametime is dependent on the framerate in the global {@link Constants} class */
    public static Duration FRAMETIME = Duration.millis(1 / ((double) FRAMERATE) * 1000);

    public static void setFrameRate(int frameRate) {
        FRAMERATE = frameRate;
        FRAMETIME = Duration.millis(1 / ((double) FRAMERATE) * 1000);
    }

    /**
     * The amount of rows in the grid. 
     * Make sure {@link Constants#ROWCOUNT} and {@link Constants#COLUMNCOUNT}, combined with {@link Constants#CELLSIZE}, is less than the width and height of the canvas.
     * Else the game will overflow out of the canvas, and you will not be able to fully see the game.
     */
    public static final int ROWCOUNT = 25;

    /**
     * The amount of columns in the grid. 
     * Make sure {@link Constants#ROWCOUNT} and {@link Constants#COLUMNCOUNT}, combined with {@link Constants#CELLSIZE}, is less than the width and height of the canvas.
     * Else the game will overflow out of the canvas, and you will not be able to fully see the game.
     */
    public static final int COLUMNCOUNT = 25;

    /**
     * The pixel size (width and height) of each cell in the grid.
     * Make sure {@link Constants#ROWCOUNT} and {@link Constants#COLUMNCOUNT}, combined with {@link Constants#CELLSIZE}, is less than the width and height of the canvas.
     * Else the game will overflow out of the canvas, and you will not be able to fully see the game.
     */
    public static final int CELLSIZE = 28;

}
