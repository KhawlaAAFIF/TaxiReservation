package com.example.taxi;

import Model.Client;
import Services.ClientService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class DriverInterface {

    @FXML
    private VBox clientsVBox;
    @FXML
    private Button logoutButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private void initialize() {
        loadAllClients();
    }


    @FXML
    private void handleViewProfileButton(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DriverProfile.fxml"));
            Parent root = loader.load();
            DriverProfile profile = loader.getController();


            Stage stage = new Stage();
            stage.setTitle("Driver Profile");
            stage.setScene(new Scene(root));


            stage.show();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private void loadAllClients() {
        ClientService clientService = new ClientService();
        List<Client> clients = clientService.getAllClients();
        clientsVBox.getChildren().clear();

        for (Client client : clients) {
            Reservation res=new Reservation(clientService.getIdres());
            HBox clientCard = createClientCard(client,res);
            clientsVBox.getChildren().add(clientCard);

        }
        scrollPane.setContent(clientsVBox);
    }

    private HBox createClientCard(Client client,Reservation reservation) {
        HBox clientCard = new HBox();
        clientCard.setSpacing(10);
        clientCard.setStyle("-fx-background-color: #f0f0f0; -fx-padding: 10;");

        VBox clientInfoVBox = new VBox();
        clientInfoVBox.setSpacing(5);

        Text clientNameText = new Text("Client: " + client.getNom());
        Text phoneNumberText = new Text("Phone Number: " + client.getTelephone());

        clientInfoVBox.getChildren().addAll(clientNameText, phoneNumberText);

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);

        Button acceptButton = new Button("Accept");
        Button declineButton = new Button("Decline");

        acceptButton.setStyle("-fx-background-color: #87FF65;");
        declineButton.setStyle("-fx-background-color: #FF0000;");

        acceptButton.setOnAction(event -> handleAcceptClient(client, clientCard,reservation));
        declineButton.setOnAction(event -> handleDeclineClient(client, clientCard,reservation));

        buttonBox.getChildren().addAll(acceptButton, declineButton);

        clientCard.getChildren().addAll(clientInfoVBox, buttonBox);

        return clientCard;
    }

    private void handleAcceptClient(Client client, HBox clientCard, Reservation reservation) {
        BdConnexion db =new BdConnexion();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Accept Client");
        alert.setHeaderText("Do you want to accept this client?");
        alert.setContentText("Client: " + client.getNom() + "\nPhone Number: " + client.getTelephone());
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        alert.getButtonTypes().setAll(yesButton, noButton);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == yesButton) {
                System.out.println("Driver accepted the client: " + client.getNom());
                clientsVBox.getChildren().remove(clientCard);
                updateReservationStatus(reservation, "Accepted");
            }
        });
    }

    public void updateReservationStatus(Reservation curRes,String status){
        BdConnexion db=new BdConnexion();
        String query="UPDATE reservation SET status=? WHERE id = ?";
        try (PreparedStatement preparedStatement = db.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(2,curRes.getId());
            preparedStatement.setString(1,status);

            int result = preparedStatement.executeUpdate();
            if (result != 1) {
                System.out.println("Failed to update reservation status for ID: " + curRes.getId());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleDeclineClient(Client client, HBox clientCard,Reservation reservation) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Decline Client");
        alert.setHeaderText("Are you sure you want to decline this client?");
        alert.setContentText("Client: " + client.getNom() + "\nPhone Number: " + client.getTelephone());

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        alert.getButtonTypes().setAll(yesButton, noButton);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == yesButton) {
                System.out.println("Driver declined the client: " + client.getNom());
                clientsVBox.getChildren().remove(clientCard);
                updateReservationStatus(reservation, "Declined");
            }
        });
    }


    @FXML
    private void handleLogoutButton(ActionEvent event) {
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
        showLoginForm();
    }

    private void showLoginForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
