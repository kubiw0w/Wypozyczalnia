package AddCar;

import Login.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import Login.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddCarController {

    @FXML
    private Button backButton;

    @FXML
    private TextField brandField;

    @FXML
    private TextField imageField;

    @FXML
    private TextField modelField;

    @FXML
    private TextField registrationField;

    @FXML
    private TextField yearField;

    @FXML
    void backScene(ActionEvent event) throws IOException {
        Login.changeScene("/MainMenu/MainMenu.fxml");
    }

    @FXML
    void addCar(ActionEvent event) {
        String brand = brandField.getText().trim();
        String model = modelField.getText().trim();
        String year = yearField.getText().trim();
        String registration = registrationField.getText().trim().toUpperCase();
        String image = imageField.getText().trim();

        if (brand.isEmpty() || model.isEmpty() || year.isEmpty() || registration.isEmpty() || image.isEmpty()) {
            showAlert("Błąd", "Wszystkie pola muszą być wypełnione.");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String insertQuery = "INSERT INTO SAMOCHODY (MARKA, MODEL, ROK_PRODUKCJI, NR_REJESTRACYJNY, ZDJECIE) " +
                    "VALUES (?, ?, ?, ?, ?)";

            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setString(1, brand);
                pstmt.setString(2, model);
                pstmt.setInt(3, Integer.parseInt(year));
                pstmt.setString(4, registration);
                pstmt.setString(5, image);
                pstmt.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            showAlert("Sukces", "Samochód został dodany pomyślnie!");
            Login.changeScene("/MainMenu/MainMenu.fxml");

        } catch (NumberFormatException | IOException e) {
            showAlert("Błąd", "Rok produkcji musi być liczbą całkowitą.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
