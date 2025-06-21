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

public class CustomerDelete {

    @FXML
    private Button backButton;

    @FXML
    private TextField idField;

    @FXML
    void backScene(ActionEvent event) throws IOException {
        Login.changeScene("/Delete/Delete.fxml");
    }

    @FXML
    void customerDelete(ActionEvent event) {
        String idText = idField.getText().trim();

        if (idText.isEmpty()) {
            showAlert("Błąd", "Wprowadź numer klienta.");
            return;
        }

        try {
            int customerId = Integer.parseInt(idText);

            try (Connection conn = DBConnection.getConnection()) {
                String selectQuery = "SELECT LICZBA_SAMOCHODOW FROM KLIENCI WHERE NUMER_KLIENTA = ?";
                try (PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
                    selectStmt.setInt(1, customerId);
                    ResultSet rs = selectStmt.executeQuery();

                    if (rs.next()) {
                        int carCount = rs.getInt("LICZBA_SAMOCHODOW");

                        if (carCount == 0) {
                            String deleteQuery = "DELETE FROM KLIENCI WHERE NUMER_KLIENTA = ?";
                            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                                deleteStmt.setInt(1, customerId);
                                int rowsAffected = deleteStmt.executeUpdate();

                                if (rowsAffected > 0) {
                                    showAlert("Sukces", "Klient został usunięty.");
                                    idField.clear();
                                    Login.changeScene("/MainMenu/MainMenu.fxml");
                                } else {
                                    showAlert("Błąd", "Nie udało się usunąć klienta.");
                                }
                            }
                        } else {
                            showAlert("Błąd", "Nie można usunąć klienta – ma wypożyczone samochody.");
                        }
                    } else {
                        showAlert("Błąd", "Nie znaleziono klienta o podanym numerze.");
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        } catch (NumberFormatException e) {
            showAlert("Błąd", "Numer klienta musi być liczbą całkowitą.");
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
