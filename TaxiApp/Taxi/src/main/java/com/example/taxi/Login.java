package com.example.taxi;

import Services.Authentification;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class Login {

    @FXML
    private Text createAccount;

    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    private Authentification authentification;

    public Login() {
        this.authentification = new Authentification();
    }
    @FXML
    private void handleCreateAccountClick(){
        try{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("signUpAs.fxml"));
        Parent root = loader.load();


        Stage stage = new Stage();
        stage.setScene(new Scene(root));


        Stage currentStage = (Stage) createAccount.getScene().getWindow();
        currentStage.close();

        stage.show();
    }catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLoginButtonClick() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Email and password cannot be empty.");
            return;
        }

        String userType = authentification.getUserType(email, password);

        if (authentification.authenticateUser(email, password)) {
            System.out.println("Login successful!");

            String fxmlFile;
            if ("driver".equals(userType)) {
                fxmlFile = "driverInterface.fxml";
            } else if ("client".equals(userType)) {
                fxmlFile = "ClientInterface.fxml";
            } else {
                System.out.println("Unknown user type");
                return;
            }

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

                ((Node)(emailField)).getScene().getWindow().hide();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Display alert for incorrect password
            showAlert("Error", "Incorrect password");
            System.out.println("Login failed!");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


}
