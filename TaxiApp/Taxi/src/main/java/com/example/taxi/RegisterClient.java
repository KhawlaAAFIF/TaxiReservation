package com.example.taxi;

import Model.Client;
import Services.ClientService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterClient implements Initializable{

    @FXML
    private TextField fullName;

    @FXML
    private TextField email;

    @FXML
    private TextField phoneNumber;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField confirmPassword;

    @FXML
    private TextField city;

    @FXML
    private ComboBox<String> sexe;

    private final ClientService clientService = new ClientService();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        sexe.getItems().addAll("Male","Female");
    }

    @FXML
    private void handleRegisterButtonClick() {
        if (isValidInput()) {
            String fullNameInput = fullName.getText();
            String emailInput = email.getText();
            String phoneNumberInput = phoneNumber.getText();
            String passwordInput = password.getText();
            String cityInput = city.getText();
            String sexeInput = sexe.getValue();
            String dateOfBirthInput = dateOfBirth.getValue().toString();

            Client client = new Client(fullNameInput, phoneNumberInput, "client", emailInput, passwordInput, dateOfBirthInput, sexeInput, cityInput);
            clientService.registerClient(client);

            Stage currentStage = (Stage) fullName.getScene().getWindow();
            currentStage.close();

            loadLoginPage();
        }
    }
    private void loadLoginPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Login");

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean isValidInput() {

    if (fullName.getText() == null || email.getText() == null || phoneNumber.getText() == null ||
            password.getText() == null || confirmPassword.getText() == null || sexe.getValue() == null ||
            dateOfBirth.getValue() == null || city.getText() == null) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Incomplete Form");
        alert.setHeaderText(null);
        alert.setContentText("Please fill in all required fields.");
        alert.showAndWait();

        return false;
    }

    String clientPassword = password.getText();
    String confirmClientPassword = confirmPassword.getText();
    if (!clientPassword.equals(confirmClientPassword)) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Password Mismatch");
        alert.setHeaderText(null);
        alert.setContentText("Password and confirm password do not match. Please try again.");
        alert.showAndWait();
        return false;
    }

    return true;
}

}
