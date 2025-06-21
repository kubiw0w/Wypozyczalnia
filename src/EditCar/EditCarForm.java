package EditCar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import Login.Login;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import Login.DBConnection;
import Login.Login;

import java.io.IOException;

public class EditCarForm {

    @FXML
    private Button backButton;

    @FXML
    private CheckBox brandCheckbox;

    @FXML
    private TextField brandField;

    @FXML
    private Text brandText;

    @FXML
    private CheckBox imageCheckbox;

    @FXML
    private TextField imageField;

    @FXML
    private Text imageText;

    @FXML
    private CheckBox modelCheckbox;

    @FXML
    private TextField modelField;

    @FXML
    private Text modelText;

    @FXML
    private CheckBox registrationCheckbox;

    @FXML
    private TextField registrationField;

    @FXML
    private Text registrationText;

    @FXML
    private Button submitButton;

    @FXML
    private CheckBox yearCheckbox;

    @FXML
    private TextField yearField;

    @FXML
    private Text yearText;

    private static String editedCarNumber;

    @FXML
    void backScene(ActionEvent event) throws IOException {
        Login.changeScene("/EditCar/EditCar.fxml");
    }

    @FXML
    private void handleBrandCheckbox() {
        boolean isBrandSelected = brandCheckbox.isSelected();
        brandText.setVisible(isBrandSelected);
        brandField.setVisible(isBrandSelected);

        boolean isModelSelected = modelCheckbox.isSelected();
        modelText.setVisible(isModelSelected);
        modelField.setVisible(isModelSelected);

        boolean isYearSelected = yearCheckbox.isSelected();
        yearText.setVisible(isYearSelected);
        yearField.setVisible(isYearSelected);

        boolean isRegistrationSelected = registrationCheckbox.isSelected();
        registrationText.setVisible(isRegistrationSelected);
        registrationField.setVisible(isRegistrationSelected);

        boolean isImageSelected = imageCheckbox.isSelected();
        imageText.setVisible(isImageSelected);
        imageField.setVisible(isImageSelected);

        if(isBrandSelected || isModelSelected || isYearSelected || isRegistrationSelected || isImageSelected){
            submitButton.setVisible(true);
        }
        else{
            submitButton.setVisible(false);
        }
    }

    public static void setEditedCarNumber(String number){
        editedCarNumber = number;
    }

    @FXML
    private void submitChanges(ActionEvent event) {
        StringBuilder queryBuilder = new StringBuilder("UPDATE SAMOCHODY SET ");
        boolean atLeastOneField = false;

        if (brandCheckbox.isSelected() && !brandField.getText().trim().isEmpty()) {
            queryBuilder.append("MARKA = ?, ");
            atLeastOneField = true;
        }
        if (modelCheckbox.isSelected() && !modelField.getText().trim().isEmpty()) {
            queryBuilder.append("MODEL = ?, ");
            atLeastOneField = true;
        }
        if (yearCheckbox.isSelected() && !yearField.getText().trim().isEmpty()) {
            queryBuilder.append("ROK_PRODUKCJI = ?, ");
            atLeastOneField = true;
        }
        if (registrationCheckbox.isSelected() && !registrationField.getText().trim().isEmpty()) {
            queryBuilder.append("NR_REJESTRACYJNY = ?, ");
            atLeastOneField = true;
        }
        if (imageCheckbox.isSelected() && !imageField.getText().trim().isEmpty()) {
            queryBuilder.append("ZDJECIE = ?, ");
            atLeastOneField = true;
        }

        if (!atLeastOneField) {
            showAlert("Błąd", "Nie wybrano żadnych danych do edycji.");
            return;
        }

        queryBuilder.setLength(queryBuilder.length() - 2);
        queryBuilder.append(" WHERE NR_REJESTRACYJNY = ?");

        try (var conn = DBConnection.getConnection();
             var pstmt = conn.prepareStatement(queryBuilder.toString())) {

            int paramIndex = 1;

            if (brandCheckbox.isSelected() && !brandField.getText().trim().isEmpty()) {
                pstmt.setString(paramIndex++, brandField.getText().trim());
            }
            if (modelCheckbox.isSelected() && !modelField.getText().trim().isEmpty()) {
                pstmt.setString(paramIndex++, modelField.getText().trim());
            }
            if (yearCheckbox.isSelected() && !yearField.getText().trim().isEmpty()) {
                pstmt.setInt(paramIndex++, Integer.parseInt(yearField.getText().trim()));
            }
            if (registrationCheckbox.isSelected() && !registrationField.getText().trim().isEmpty()) {
                pstmt.setString(paramIndex++, registrationField.getText().trim());
            }
            if (imageCheckbox.isSelected() && !imageField.getText().trim().isEmpty()) {
                pstmt.setString(paramIndex++, imageField.getText().trim());
            }

            pstmt.setString(paramIndex, editedCarNumber);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                showAlert("Sukces", "Dane zostały zaktualizowane.");
                Login.changeScene("/MainMenu/MainMenu.fxml");
            } else {
                showAlert("Błąd", "Nie udało się zaktualizować danych.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Błąd", "Wystąpił problem z aktualizacją danych.");
        }
    }

    private void showAlert(String title, String content) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
