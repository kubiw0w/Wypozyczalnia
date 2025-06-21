package AddCustomer;

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

public class AddCustomerController {

    @FXML
    private Button backButton;

    @FXML
    private TextField emailField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField surnameField;

    @FXML
    void addCustomer(ActionEvent event) {
        String name = nameField.getText().trim();
        String surname = surnameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();

        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            showAlert("Błąd", "Wszystkie pola muszą być wypełnione.");
            return;
        }

        try {
            Connection conn = DBConnection.getConnection();
            int newClientNumber = 100001; // domyślny początek

            String getMaxQuery = "SELECT MAX(NUMER_KLIENTA) FROM KLIENCI";
            try (PreparedStatement maxStmt = conn.prepareStatement(getMaxQuery)) {
                var rs = maxStmt.executeQuery();
                if (rs.next()) {
                    int max = rs.getInt(1);
                    if (!rs.wasNull()) {
                        newClientNumber = max + 1;
                    }
                }
            }

            String insertQuery = "INSERT INTO KLIENCI (NUMER_KLIENTA, IMIE, NAZWISKO, EMAIL, TELEFON) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
                pstmt.setInt(1, newClientNumber);
                pstmt.setString(2, name);
                pstmt.setString(3, surname);
                pstmt.setString(4, email);
                pstmt.setString(5, phone);
                pstmt.executeUpdate();
            }

            showAlert("Sukces", "Klient został dodany pomyślnie! Numer klienta: " + newClientNumber);
            Login.changeScene("/MainMenu/MainMenu.fxml");

        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Błąd", "Wystąpił problem podczas dodawania klienta.");
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Błąd", "Wystąpił problem z przełączeniem sceny.");
        }
    }

    @FXML
    void backScene(ActionEvent event) throws IOException {
        Login.changeScene("/MainMenu/MainMenu.fxml");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
