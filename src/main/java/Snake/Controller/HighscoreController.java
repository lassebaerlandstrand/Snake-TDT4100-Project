package Snake.Controller;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Snake.App;
import Snake.Data.Highscore;
import Snake.Data.HighscoreObject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class HighscoreController {

    @FXML
    private ListView<ListCell> listView;

    // This is a custom cell for the ListView, this enables us to left align time and right align score
    public static class ListCell extends HBox {
        Label label1 = new Label();
        Label label2 = new Label();

        ListCell(String label1, String label2) {
            super();
            this.label1.setText(label1);
            this.label2.setText(label2);
            this.label1.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(this.label1, Priority.ALWAYS);

            this.getChildren().addAll(this.label1, this.label2);
        }
    }

    @FXML
    private void initialize() {
        List<HighscoreObject> highscoreList = new Highscore("highscore.txt").readAllScores();
        Collections.sort(highscoreList);
        List<ListCell> listCells = new ArrayList<>();

        for (HighscoreObject highscoreObject : highscoreList) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm:ss");
            String time = highscoreObject.getTime().format(formatter);
            String playerType = highscoreObject.getPlayerType();
            String score = String.valueOf(highscoreObject.getScore());
            listCells.add(new ListCell(String.format("%s (%s)", time, playerType), score));
        }

        listView.getItems().addAll(FXCollections.observableArrayList(listCells));
    }

    @FXML
    private void switchToMain() throws IOException {
        App.setRoot("App");
    }

}
