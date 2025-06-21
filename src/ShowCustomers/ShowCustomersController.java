package ShowCustomers;

import Login.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShowCustomersController implements Initializable {
    @FXML
    private Button backButton;

    @FXML
    private ScrollPane customersList;

    @FXML
    void backScene(ActionEvent event) throws IOException {
        Login.changeScene("/MainMenu/MainMenu.fxml");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<Klienci> klienci = KlienciDAO.getAllCustomers();

            VBox content = new VBox(10);
            content.setPadding(new Insets(10));

            for (Klienci k : klienci) {
                VBox carBox = new VBox(5);
                carBox.setStyle("-fx-border-color: gray; -fx-padding: 10;");

                Label info = new Label(
                        k.getName() + " " + k.getSurname() + "\n" +
                                "Email: " + k.getEmail() + "\n" +
                                "Telefon: " + k.getPhone() + "\n" +
                                "Identyfikator klienta: " + k.getNumber()
                );

                carBox.getChildren().add(info);
                content.getChildren().add(carBox);
            }

            customersList.setContent(content);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
