package com.example.taxi;

import Model.Client;
import Services.Authentification;
import Services.ClientService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class UpdateClient {
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;

    @FXML
    private TextField cityField;
    @FXML
    private Button submitBut;

    private Client currentClient;
    private ClientService clientService;

    private int id=Authentification.id;



    public UpdateClient() {
        this.clientService = new ClientService();
    }

    @FXML
    public void initialize() {

        currentClient = clientService.getClient(id);
        System.out.println("Logged-in user ID: " + id);
     
        if (currentClient != null) {
            System.out.println("Client information is available");
            afficherInfoClient(currentClient);
        } else {
            System.out.println("Client information is not available");
        }
    }

    public void afficherInfoClient(Client client){

        nameField.setText(client.getNom());
        emailField.setText(client.getEmail());
        phoneField.setText(client.getTelephone());
        cityField.setText(client.getCity());
    }
    @FXML
    public void handleSubmitButtonAction() {

        submitBut.setOnAction(event -> {
            currentClient.setNom(nameField.getText());
            currentClient.setEmail(emailField.getText());
            currentClient.setTelephone(phoneField.getText());
            currentClient.setCity(cityField.getText());
            currentClient.setId(id);

            if (currentClient != null) {
                Client updatedClient = clientService.updateClient(currentClient);
                if (updatedClient != null) {
                    showAlert("Success", "Client updated successfully!");
                    Stage cstage = (Stage) submitBut.getScene().getWindow();
                    cstage.close();
                    showClientInterfaceForm();
                } else {
                    showAlert("Error", "Failed to update client.");
                }
            } else {
                showAlert("Error", "No client founded.");
            }
        });
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void showClientInterfaceForm(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientInterface.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
