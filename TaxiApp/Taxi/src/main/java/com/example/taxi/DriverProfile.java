package com.example.taxi;

import Model.Driver;
import Services.Authentification;
import Services.DriverService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DriverProfile {

    @FXML
    private TextField fullNameTextField;
    @FXML
    private TextField phoneTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField cityTextField;
    @FXML
    private TextField carTypeTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button submitButton;

    private DriverService driverService;
    private Driver currentDriver;
    private int id=Authentification.id;

    public DriverProfile() {
        this.driverService = new DriverService();
    }

    @FXML
    private void initialize() {
        currentDriver =driverService.getDriver(id);
        // Load driver data based on the email
        loadDriverData(currentDriver);
    }

    private void loadDriverData(Driver driver) {


        // Display the driver's data in the JavaFX interface
        fullNameTextField.setText(currentDriver.getNom());
        phoneTextField.setText(currentDriver.getTelephone());
        emailTextField.setText(currentDriver.getEmail());
        cityTextField.setText(currentDriver.getCity());
        carTypeTextField.setText(currentDriver.getCarType());
        passwordField.setText(currentDriver.getPassword());

    }

    @FXML
    private void handleSaveButtonClick() {
        submitButton.setOnAction(event -> {
            try {
                currentDriver.setNom(fullNameTextField.getText());
                currentDriver.setEmail(emailTextField.getText());
                currentDriver.setTelephone(phoneTextField.getText());
                currentDriver.setPassword(passwordField.getText());
                currentDriver.setCity(cityTextField.getText());
                currentDriver.setId(id);

                if (currentDriver != null) {
                    Driver updatedDriver = driverService.updateDriver(currentDriver);


                        showAlert("Success", "Client updated successfully!");
                        Stage cstage = (Stage) submitButton.getScene().getWindow();
                        cstage.close();


                } else {
                    showAlert("Error", "No client found.");
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Handle or log the exception appropriately
                showAlert("Error", "An error occurred while updating the client.");
            }
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

