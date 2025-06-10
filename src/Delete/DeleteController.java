package Delete;

import Login.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class DeleteController {

    @FXML
    private Button backButton;

    @FXML
    void backScene(ActionEvent event) throws IOException {
        Login.changeScene("/MainMenu/MainMenu.fxml");
    }

    @FXML
    void carScene(ActionEvent event) throws IOException {
        Login.changeScene("/Delete/CarDelete.fxml");
    }

    @FXML
    void customerScene(ActionEvent event) throws IOException {
        Login.changeScene("/Delete/CustomerDelete.fxml");
    }
}
