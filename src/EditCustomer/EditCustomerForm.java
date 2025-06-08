package EditCustomer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import Login.Login;
import Login.DBConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class EditCustomerForm {

    @FXML
    private Button backButton;

    @FXML
    private CheckBox emailCheckbox;

    @FXML
    private TextField emailField;

    @FXML
    private Text emailText;

    @FXML
    private CheckBox nameCheckbox;

    @FXML
    private TextField nameField;

    @FXML
    private Text nameText;

    @FXML
    private CheckBox phoneCheckbox;

    @FXML
    private TextField phoneField;

    @FXML
    private Text phoneText;

    @FXML
    private Button submitButton;

    @FXML
    private CheckBox surnameCheckbox;

    @FXML
    private TextField surnameField;

    @FXML
    private Text surnameText;

    private static Integer editedCustomerNumber;

    @FXML
    void backScene(ActionEvent event) throws IOException {
        Login.changeScene("/EditCustomer/EditCustomer.fxml");
    }

    public static void setEditedCustomerNumber(int number) {
        editedCustomerNumber = number;
    }

    @FXML
    private void handleBrandCheckbox() {
        boolean isNameSelected = nameCheckbox.isSelected();
        nameText.setVisible(isNameSelected);
        nameField.setVisible(isNameSelected);

        boolean isSurnameSelected = surnameCheckbox.isSelected();
        surnameText.setVisible(isSurnameSelected);
        surnameField.setVisible(isSurnameSelected);

        boolean isEmailSelected = emailCheckbox.isSelected();
        emailText.setVisible(isEmailSelected);
        emailField.setVisible(isEmailSelected);

        boolean isPhoneSelected = phoneCheckbox.isSelected();
        phoneText.setVisible(isPhoneSelected);
        phoneField.setVisible(isPhoneSelected);

        if(isNameSelected || isSurnameSelected || isEmailSelected || isPhoneSelected){
            submitButton.setVisible(true);
        }
        else{
            submitButton.setVisible(false);
        }
    }

    @FXML
    void submitChanges(ActionEvent event) {
        StringBuilder queryBuilder = new StringBuilder("UPDATE KLIENCI SET ");
        boolean atLeastOneField = false;

        if (nameCheckbox.isSelected() && !nameField.getText().trim().isEmpty()) {
            queryBuilder.append("IMIE = ?, ");
            atLeastOneField = true;
        }
        if (surnameCheckbox.isSelected() && !surnameField.getText().trim().isEmpty()) {
            queryBuilder.append("NAZWISKO = ?, ");
            atLeastOneField = true;
        }
        if (emailCheckbox.isSelected() && !emailField.getText().trim().isEmpty()) {
            queryBuilder.append("EMAIL = ?, ");
            atLeastOneField = true;
        }
        if (phoneCheckbox.isSelected() && !phoneField.getText().trim().isEmpty()) {
            queryBuilder.append("TELEFON = ?, ");
            atLeastOneField = true;
        }

        if (!atLeastOneField) {
            showAlert("Błąd", "Nie wybrano żadnych danych do edycji.");
            return;
        }

        // Usuń ostatni przecinek i spację
        queryBuilder.setLength(queryBuilder.length() - 2);
        queryBuilder.append(" WHERE NUMER_KLIENTA = ?");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(queryBuilder.toString())) {

            int paramIndex = 1;

            if (nameCheckbox.isSelected()) {
                pstmt.setString(paramIndex++, nameField.getText().trim());
            }
            if (surnameCheckbox.isSelected()) {
                pstmt.setString(paramIndex++, surnameField.getText().trim());
            }
            if (emailCheckbox.isSelected()) {
                pstmt.setString(paramIndex++, emailField.getText().trim());
            }
            if (phoneCheckbox.isSelected()) {
                pstmt.setString(paramIndex++, phoneField.getText().trim());
            }

            pstmt.setInt(paramIndex, editedCustomerNumber);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                showAlert("Sukces", "Dane klienta zostały zaktualizowane.");
                Login.changeScene("/MainMenu/MainMenu.fxml");
            } else {
                showAlert("Błąd", "Nie udało się zaktualizować danych.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Błąd", "Wystąpił problem podczas aktualizacji danych.");
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
