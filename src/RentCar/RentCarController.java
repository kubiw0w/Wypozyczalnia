package RentCar;

import Login.Login;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import Login.DBConnection;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class RentCarController {

    @FXML
    private Button backButton;

    @FXML
    private DatePicker beginDate;

    @FXML
    private ComboBox<CarItem> carCombo;

    @FXML
    private ComboBox<ClientItem> customerCombo;

    @FXML
    private DatePicker endDate;

    @FXML
    public void initialize() {
        loadCustomers();
        loadCars();

        restrictPastDates(beginDate);
        restrictMinDate(endDate, LocalDate.now());

        beginDate.valueProperty().addListener((obs, oldDate, newDate) -> {
            if (newDate != null) {
                restrictMinDate(endDate, newDate);
            }
        });
    }

    private void loadCustomers() {
        ObservableList<ClientItem> clients = FXCollections.observableArrayList();

        String query = "SELECT NUMER_KLIENTA, IMIE, NAZWISKO FROM KLIENCI ORDER BY NAZWISKO";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("NUMER_KLIENTA");
                String imie = rs.getString("IMIE");
                String nazwisko = rs.getString("NAZWISKO");

                clients.add(new ClientItem(id, imie, nazwisko));
            }

            customerCombo.setItems(clients);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadCars() {
        ObservableList<CarItem> cars = FXCollections.observableArrayList();

        String query = "SELECT NR_REJESTRACYJNY, MARKA, MODEL FROM SAMOCHODY WHERE DOSTĘPNY = 'T' ORDER BY MARKA";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String nr = rs.getString("NR_REJESTRACYJNY");
                String marka = rs.getString("MARKA");
                String model = rs.getString("MODEL");

                cars.add(new CarItem(nr, marka, model));
            }

            carCombo.setItems(cars);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void restrictPastDates(DatePicker datePicker) {
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #eeeeee;");
                }
            }
        });

        datePicker.setValue(LocalDate.now());
    }

    private void restrictMinDate(DatePicker datePicker, LocalDate minDate) {
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(minDate)) {
                    setDisable(true);
                    setStyle("-fx-background-color: #eeeeee;");
                }
            }
        });

        if (datePicker.getValue() == null || datePicker.getValue().isBefore(minDate)) {
            datePicker.setValue(minDate);
        }
    }

    @FXML
    void rentCar(ActionEvent event) {
        ClientItem selectedClient = customerCombo.getValue();
        CarItem selectedCar = carCombo.getValue();
        LocalDate startDate = beginDate.getValue();
        LocalDate endDateVal = endDate.getValue();

        if (selectedClient == null || selectedCar == null || startDate == null || endDateVal == null) {
            showAlert("Błąd", "Uzupełnij wszystkie pola.");
            return;
        }

        if (endDateVal.isBefore(startDate)) {
            showAlert("Błąd", "Data zwrotu nie może być wcześniejsza niż data wypożyczenia.");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            int carId;
            String getCarIdQuery = "SELECT ID_SAMOCHODU, DOSTĘPNY FROM SAMOCHODY WHERE NR_REJESTRACYJNY = ?";
            try (PreparedStatement stmt = conn.prepareStatement(getCarIdQuery)) {
                stmt.setString(1, selectedCar.getNrRejestracyjny());
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    carId = rs.getInt("ID_SAMOCHODU");
                    String dostepny = rs.getString("DOSTĘPNY");
                    if (!"T".equalsIgnoreCase(dostepny)) {
                        showAlert("Błąd", "Wybrany samochód nie jest dostępny.");
                        return;
                    }
                } else {
                    showAlert("Błąd", "Nie znaleziono samochodu.");
                    return;
                }
            }

            int clientId;
            String getClientIdQuery = "SELECT ID_KLIENTA FROM KLIENCI WHERE NUMER_KLIENTA = ?";
            try (PreparedStatement stmt = conn.prepareStatement(getClientIdQuery)) {
                stmt.setInt(1, selectedClient.getNumerKlienta());
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    clientId = rs.getInt("ID_KLIENTA");
                } else {
                    showAlert("Błąd", "Nie znaleziono klienta.");
                    return;
                }
            }

            String insertRental = "INSERT INTO WYPOZYCZENIA (ID_WYPOZYCZENIA, ID_SAMOCHODU, ID_KLIENTA, DATA_WYPOZYCZENIA, DATA_ZWROTU) " +
                    "VALUES (WYPOZYCZENIA_SEQ.NEXTVAL, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(insertRental)) {
                stmt.setInt(1, carId);
                stmt.setInt(2, clientId);
                stmt.setDate(3, java.sql.Date.valueOf(startDate));
                stmt.setDate(4, java.sql.Date.valueOf(endDateVal));
                stmt.executeUpdate();
            }

            String updateCarStatus = "UPDATE SAMOCHODY SET DOSTĘPNY = 'N' WHERE ID_SAMOCHODU = ?";
            try (PreparedStatement stmt = conn.prepareStatement(updateCarStatus)) {
                stmt.setInt(1, carId);
                stmt.executeUpdate();
            }

            String updateClientCount = "UPDATE KLIENCI SET LICZBA_SAMOCHODOW = LICZBA_SAMOCHODOW + 1 WHERE ID_KLIENTA = ?";
            try (PreparedStatement stmt = conn.prepareStatement(updateClientCount)) {
                stmt.setInt(1, clientId);
                stmt.executeUpdate();
            }

            conn.commit();

            showAlert("Sukces", "Wypożyczenie zostało zapisane.");
            Login.changeScene("/MainMenu/MainMenu.fxml");

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Błąd", "Wystąpił problem podczas zapisu do bazy.");
        }
    }


    @FXML
    void backScene(ActionEvent event) throws IOException {
        Login.changeScene("/MainMenu/MainMenu.fxml");
    }

    private void showAlert(String tytul, String tresc) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(tytul);
        alert.setHeaderText(null);
        alert.setContentText(tresc);
        alert.showAndWait();
    }

}
