package ShowCars;

import Login.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShowCarsController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private ScrollPane carList;

    @FXML
    void backScene(ActionEvent event) throws IOException {
        Login.changeScene("/MainMenu/MainMenu.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<Samochod> samochody = SamochodDAO.getAllCars();

            VBox content = new VBox(10);
            content.setPadding(new Insets(10));

            for (Samochod s : samochody) {
                VBox carBox = new VBox(5);
                carBox.setStyle("-fx-border-color: gray; -fx-padding: 10;");

                Label info = new Label(
                        s.getBrand() + " " + s.getModel() + " (" + s.getYear() + ")\n" +
                                "Rejestracja: " + s.getRegistrationNr() + "\n" +
                                "Dostępny: " + (s.isAvailable() ? "Tak" : "Nie")
                );

                ImageView imageView = new ImageView();
                try {
                    Image image = new Image(getClass().getResourceAsStream("/" + s.getImage()));
                    imageView.setImage(image);
                    imageView.setFitWidth(500);
                    imageView.setPreserveRatio(true);
                } catch (Exception e) {
                    imageView.setImage(null);
                }


                carBox.getChildren().addAll(imageView, info);
                content.getChildren().add(carBox);
            }

            carList.setContent(content);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}