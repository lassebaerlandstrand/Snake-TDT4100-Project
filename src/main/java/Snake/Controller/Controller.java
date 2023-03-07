package Snake.Controller;

import java.io.IOException;

import Snake.App;
import javafx.fxml.FXML;

public interface Controller {

    @FXML
    private void switchToMain() throws IOException {
        App.setRoot("App");
    }

}
