package Delete;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import Login.Login;
import Login.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarDelete {

    @FXML
    private Button backButton;

    @FXML
    private TextField carField;

    @FXML
    void backScene(ActionEvent event) throws IOException {
        Login.changeScene("/Delete/Delete.fxml");
    }

    @FXML
    void carDelete(ActionEvent event) {
        String registrationNr = carField.getText().trim();

        if (registrationNr.isEmpty()) {
            showAlert("Błąd", "Wprowadź numer rejestracyjny.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            String selectQuery = "SELECT DOSTĘPNY FROM SAMOCHODY WHERE UPPER(TRIM(NR_REJESTRACYJNY)) = UPPER(?)";
            try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
                selectStmt.setString(1, registrationNr);
                ResultSet rs = selectStmt.executeQuery();

                if (rs.next()) {
                    String available = rs.getString("DOSTĘPNY");

                    if ("T".equalsIgnoreCase(available)) {
                        String deleteQuery = "DELETE FROM SAMOCHODY WHERE UPPER(TRIM(NR_REJESTRACYJNY)) = UPPER(?)";
                        try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                            deleteStmt.setString(1, registrationNr);
                            int rowsAffected = deleteStmt.executeUpdate();

                            if (rowsAffected > 0) {
                                showAlert("Sukces", "Samochód został usunięty.");
                                carField.clear();
                                Login.changeScene("/MainMenu/MainMenu.fxml");
                            } else {
                                showAlert("Błąd", "Nie udało się usunąć samochodu.");
                            }
                        }
                    } else {
                        showAlert("Błąd", "Nie można usunąć samochodu – jest aktualnie wypożyczony.");
                    }
                } else {
                    showAlert("Błąd", "Nie znaleziono samochodu o podanym numerze rejestracyjnym.");
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            showAlert("Błąd", "Wystąpił błąd przy połączeniu z bazą danych.");
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
