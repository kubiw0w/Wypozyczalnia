package MainMenu;

import Login.Login;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class MainMenuController {

    @FXML
    private Button addCarButton;

    @FXML
    private Button addCustomerButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editCarButton;

    @FXML
    private Button editCustomerButton;

    @FXML
    private Button rentCarButton;

    @FXML
    private Button showButton;

    @FXML
    private Button showCustomersButton;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();
        String buttonId = clickedButton.getId();

        switch (buttonId) {
            case "addCarButton":
                Login.changeScene("/AddCar/AddCar.fxml");
                break;
            case "addCustomerButton":
                Login.changeScene("/AddCustomer/AddCustomer.fxml");
                break;
            case "deleteButton":
                Login.changeScene("/Delete/Delete.fxml");
                break;
            case "editCarButton":
                Login.changeScene("/EditCar/EditCar.fxml");
                break;
            case "editCustomerButton":
                Login.changeScene("/EditCustomer/EditCustomer.fxml");
                break;
            case "rentCarButton":
                Login.changeScene("/RentCar/RentCar.fxml");
                break;
            case "showButton":
                Login.changeScene("/ShowCars/ShowCars.fxml");
                break;
            case "showCustomersButton":
                Login.changeScene("/ShowCustomers/ShowCustomers.fxml");
                break;
            default:
                System.out.println("Nieznany przycisk!");
                return;
        }
    }
}
