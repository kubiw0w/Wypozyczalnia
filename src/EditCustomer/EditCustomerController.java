package EditCustomer;

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

public class EditCustomerController {

    @FXML
    private Button backButton;

    @FXML
    private Button editButton;

    @FXML
    private TextField idField;

    @FXML
    void backScene(ActionEvent event) throws IOException {
        Login.changeScene("/MainMenu/MainMenu.fxml");
    }

    public static boolean isCustomerExist(int customerNumber) throws SQLException {
        String query = "SELECT COUNT(*) FROM KLIENCI WHERE NUMER_KLIENTA = ?";
        Connection conn = DBConnection.getConnection();

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, customerNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                System.out.println("Liczba pasujących klientów: " + count);
                return count > 0;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @FXML
    void editCustomer(ActionEvent event) {
        String input = idField.getText().trim();

        if (input.isEmpty()) {
            showAlert("Błąd", "Wprowadź numer klienta.");
            return;
        }

        try {
            int customerNumber = Integer.parseInt(input);

            if (isCustomerExist(customerNumber)) {
                EditCustomerForm.setEditedCustomerNumber(customerNumber);
                Login.changeScene("/EditCustomer/EditCustomerForm.fxml");
            } else {
                showAlert("Nie znaleziono", "Nie znaleziono klienta o podanym numerze.");
            }

        } catch (NumberFormatException e) {
            showAlert("Błąd", "Numer klienta musi być liczbą.");
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
