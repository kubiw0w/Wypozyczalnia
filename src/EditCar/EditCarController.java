package EditCar;

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
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditCarController {

    @FXML
    private Button backButton;

    @FXML
    private Button editButton;

    @FXML
    private TextField registrationField;

    @FXML
    void backScene(ActionEvent event) throws IOException {
        Login.changeScene("/MainMenu/MainMenu.fxml");
    }

    public static boolean isCarExist(String nrRej) throws SQLException {
        String query = "SELECT COUNT(*) FROM SAMOCHODY WHERE UPPER(TRIM(NR_REJESTRACYJNY)) = UPPER(?)";
        Connection conn = DBConnection.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, nrRej.trim());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Liczba pasujących samochodów: " + count);
                return count > 0;
            }
        }
        return false;
    }

    @FXML
    void editCar(ActionEvent event) {
        String registrationNr = registrationField.getText().trim();

        if (registrationNr.isEmpty()) {
            showAlert("Błąd", "Wprowadź numer rejestracyjny.");
            return;
        }

        try {
            if (isCarExist(registrationNr)) {
                EditCarForm.setEditedCarNumber(registrationNr);
                Login.changeScene("/EditCar/EditCarForm.fxml");
            } else {
                showAlert("Nie znaleziono", "Nie znaleziono samochodu o takim numerze rejestracyjnym.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Błąd", "Wystąpił problem podczas sprawdzania bazy danych.");
        }
    }

    private void showAlert(String tytul, String tresc) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(tytul);
        alert.setHeaderText(null);
        alert.setContentText(tresc);
        alert.showAndWait();
    }
}
