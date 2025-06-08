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

        Connection conn = tryConnect(username, password);
        if (conn != null) {
            DBConnection.setCredentials(username, password);
            showAlert("Sukces", "Zalogowano pomyślnie!");
            Login.changeScene("/MainMenu/MainMenu.fxml");
        } else {
            showAlert("Błąd", "Nieprawidłowy login lub hasło.");
        }
    }

    private Connection tryConnect(String username, String password) {
        String url = "jdbc:oracle:thin:@//localhost:1521/orclpdb";

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
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
