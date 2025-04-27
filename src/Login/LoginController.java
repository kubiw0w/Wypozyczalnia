package Login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (canConnect(username, password)) {
            showAlert("Sukces", "Zalogowano pomyślnie!");
            Login.changeScene("/MainMenu/MainMenu.fxml");
        } else {
            showAlert("Błąd", "Nieprawidłowy login lub hasło.");
        }
    }

    private boolean canConnect(String username, String password) {
        String jdbcUrl = "jdbc:oracle:thin:@//localhost:1521/orclpdb";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password)) {
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
