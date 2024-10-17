package com.example.taxi;

import Model.Driver;
import Services.DriverService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class RegisterDriver implements Initializable {
    @FXML
    private TextField name;

    @FXML
    private TextField email;

    @FXML
    private TextField phoneNumber;

    @FXML
    private PasswordField password;

    @FXML PasswordField confirmPassword;

    @FXML
    private TextField carType;

    @FXML
    private ComboBox<String> sexe;

    @FXML
    private DatePicker birthDate;

    @FXML
    private TextField city;

    private DriverService driverService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sexe.getItems().addAll("Male", "Female");
        driverService = new DriverService();

    }

    @FXML
    private void handleRegistration() {
        if (isValidInput()) {

            Driver driver = new Driver();
            driver.setNom(name.getText());
            driver.setEmail(email.getText());
            driver.setTelephone(phoneNumber.getText());
            driver.setPassword(password.getText());
            driver.setCarType(carType.getText());
            driver.setSexe(sexe.getValue());
            driver.setDateDeNaissance(LocalDate.parse(birthDate.getValue().toString()));
            driver.setCity(city.getText());


            driverService.registerDriver(driver);
            Stage currentStage = (Stage) name.getScene().getWindow();
            currentStage.close();
            loadLoginPage();

        }
    }

    private boolean isValidInput() {
        if (name.getText() == null || email.getText() == null || phoneNumber.getText() == null ||
                password.getText() == null || confirmPassword.getText() == null || sexe.getValue() == null ||
                birthDate.getValue() == null || city.getText() == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Incomplete Form");
            alert.setHeaderText(null);
            alert.setContentText("Please fill in all required fields.");
            alert.showAndWait();

            return false;
        }

        String driverPassword = password.getText();
        String confirmDriverPassword = confirmPassword.getText();
        if (!driverPassword.equals(confirmDriverPassword)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Password Mismatch");
            alert.setHeaderText(null);
            alert.setContentText("Password and confirm password do not match. Please try again.");
            alert.showAndWait();
            return false;
        }
        return true;
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
}

