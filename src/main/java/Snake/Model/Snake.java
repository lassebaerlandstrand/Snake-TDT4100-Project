package Snake;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;

public class Snake {

    List<SnakeCell> snakeCells = new ArrayList<SnakeCell>();

    // https://colors.artyclick.com/color-names-dictionary/colors/6CBB3C/
    private Color headColor = Color.web("#487C27");
    private Color bodyColor = Color.web("#6CBA3B");

    public Snake() {

    }

    private int[] getTailPosition() {
        SnakeCell lastCell = snakeCells.get(snakeCells.size() - 1);
        return new int[] { lastCell.getX(), lastCell.getY() };
    }

    public void grow() {
        SnakeCell cell = new SnakeCell(0, 0, bodyColor);
    }

}
