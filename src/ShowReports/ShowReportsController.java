package ShowReports;

import RentCar.ClientItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import Login.Login;
import Login.DBConnection;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ShowReportsController {

    @FXML
    private Button backButton;

    @FXML
    private ComboBox<ClientItem> customerCombo;

    @FXML
    private ScrollPane reportsList;

    @FXML
    private Button submitButton;

    @FXML
    private VBox reportContainer;


    @FXML
    void backScene(ActionEvent event) throws IOException {
        Login.changeScene("/MainMenu/MainMenu.fxml");
    }

    @FXML
    public void initialize(){
        loadCustomers();
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

    @FXML
    void showReportsAction(ActionEvent event) {
        ClientItem selectedClient = customerCombo.getValue();

        if (selectedClient == null) {
            return;
        }

        int clientNumber = selectedClient.getNumerKlienta();

        String query = "SELECT IMIE, NAZWISKO, EMAIL, TELEFON, NUMER_KLIENTA, " +
                "MARKA, MODEL, ROK_PRODUKCJI, NR_REJESTRACYJNY, DATA_WYPOZYCZENIA, DATA_ZWROTU " +
                "FROM RAPORTY WHERE NUMER_KLIENTA = " + clientNumber + " ORDER BY DATA_WYPOZYCZENIA DESC";

        VBox content = new VBox(10); // odstęp między rekordami

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String entry = String.format(
                        "Klient: %s %s (%s, tel. %s)\nNumer klienta: %s\nSamochód: %s %s (%s, %d)\n" +
                                "Data wypożyczenia: %s\nData zwrotu: %s",
                        rs.getString("IMIE"),
                        rs.getString("NAZWISKO"),
                        rs.getString("EMAIL"),
                        rs.getString("TELEFON"),
                        rs.getString("NUMER_KLIENTA"),
                        rs.getString("MARKA"),
                        rs.getString("MODEL"),
                        rs.getString("NR_REJESTRACYJNY"),
                        rs.getInt("ROK_PRODUKCJI"),
                        rs.getDate("DATA_WYPOZYCZENIA"),
                        rs.getDate("DATA_ZWROTU")
                );

                Label label = new Label(entry);
                label.setStyle("-fx-padding: 10; -fx-border-color: lightgray; -fx-border-radius: 5;");
                content.getChildren().add(label);
            }

            reportsList.setContent(content);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
