package com.example.taxi;

import Model.Driver;
import Services.DriverService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
public class ClientInterface {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Button logoutBut;
    @FXML
    private Button updatePr;
    @FXML
    private Button reserv;

    public static  int DriverId;
    private DriverService driverService;

    public ClientInterface() {
        this.driverService = new DriverService();
    }

    @FXML
    public void initialize() {
        afficherTousConducteurs();
    }

    public void afficherTousConducteurs() {
        List<Driver> drivers = driverService.getAllDrivers();

        double layoutX = 75.0;
        double layoutY = 150.0;
        double maxPaneHeight = 510.0;
        double prefHeight=107.0;
        for (Driver conducteur : drivers) {
            Pane drivPane = createDriverPane(conducteur);
            drivPane.setLayoutX(layoutX);
            drivPane.setLayoutY(layoutY);
            mainPane.getChildren().add(drivPane);

            layoutY += 120.0;
            if (layoutY+prefHeight >= maxPaneHeight) {
                layoutX += 300.0;
                layoutY = 150.0;
            }
        }
    }

    private Pane createDriverPane(Driver conducteur) {
        Pane pane = new Pane();
        pane.setLayoutX(75.0);
        pane.setPrefHeight(107.0);
        pane.setPrefWidth(200.0);
        pane.setStyle("-fx-background-color: white;");


        String imagesPath = "file:Taxi/src/main/resources/images/";
        String[] iconUrls = {
                imagesPath + "car1.png",
                imagesPath + "car2.png",
                imagesPath + "car3.png",
                imagesPath + "car4.png"
        };

        int randomIconIndex = (int) (Math.random() * iconUrls.length);
        String randomIconUrl = iconUrls[randomIconIndex];

        ImageView iconView = new ImageView(new Image(randomIconUrl));
        iconView.setLayoutX(10.0);
        iconView.setLayoutY(24.0);
        iconView.setFitWidth(46.0);
        iconView.setFitHeight(37.0);

        Text nomConducteur = new Text();
        nomConducteur.setLayoutX(66.0);
        nomConducteur.setLayoutY(19.0);
        nomConducteur.setText(conducteur.getNom());


        Text modeleVoiture = new Text();
        modeleVoiture.setLayoutX(80.0);
        modeleVoiture.setLayoutY(45.0);
        modeleVoiture.setText(conducteur.getCarType());

        Button declineButton = new Button("Decline");
        declineButton.setLayoutX(31.0);
        declineButton.setLayoutY(71.0);
        declineButton.setOnAction(event -> {
            mainPane.getChildren().remove(pane);
            ObservableList<Node> children = mainPane.getChildren();
            double currentLayoutY = 150.0;
            double currentLayoutX = 75.0;
            double maxPaneHeight = 510.0;
            double paneHeight = 120.0;
            double paneWidth = 300.0;

            for (Node child : children) {
                if (child instanceof Pane) {
                    child.setLayoutX(currentLayoutX);
                    child.setLayoutY(currentLayoutY);

                    currentLayoutY += paneHeight;
                    if (currentLayoutY >= maxPaneHeight) {
                        currentLayoutY = 150.0;
                        currentLayoutX += paneWidth;

                    }
                }
            }
        });

        Button acceptButton = new Button("Accept");
        acceptButton.setLayoutX(110.0);
        acceptButton.setLayoutY(71.0);
        acceptButton.setStyle("-fx-background-color: #87FF65;");
        acceptButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Accept Driver");
            alert.setHeaderText("Do you want to accept this driver?");
            alert.setContentText("Driver: " + conducteur.getNom() + "\n Car type: " + conducteur.getCarType());

            ButtonType yesButton = new ButtonType("Yes");
            ButtonType noButton = new ButtonType("No");
            alert.getButtonTypes().setAll(yesButton, noButton);


            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == yesButton) {
                    DriverId=conducteur.getId();
                    System.out.println("Driver accepted id : " + DriverId);
                    System.out.println("Driver accepted : " + conducteur.getNom());



                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Reservation.fxml"));
                        Parent root = loader.load();

                        Scene scene = new Scene(root);
                        Stage stage = (Stage) acceptButton.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        });

        pane.getChildren().addAll(iconView, nomConducteur, modeleVoiture, declineButton, acceptButton);

        return pane;
    }



    public void handlereservationButton(){
        Stage stage = (Stage) reserv.getScene().getWindow();
        stage.close();
        showFrame("MyReservations.fxml");
    }
    @FXML
    public void handleButtonBack(MouseEvent event) {
        logoutBut.setOnMouseEntered(event1 -> {
            logoutBut.setStyle("-fx-background-color: #8DB600;");
        });
        updatePr.setOnMouseEntered(event1 -> {
            updatePr.setStyle("-fx-background-color: #8DB600;");
        });
        reserv.setOnMouseEntered(event1 -> {
            reserv.setStyle("-fx-background-color: #8DB600;");
        });
    }

    @FXML
    public void handleButtonBackg(MouseEvent event) {
        logoutBut.setOnMouseExited(event1 -> {
            logoutBut.setStyle("-fx-background-color: transparent;");
        });
        updatePr.setOnMouseExited(event1 -> {
            updatePr.setStyle("-fx-background-color: transparent;");
        });
        reserv.setOnMouseExited(event1 -> {
            reserv.setStyle("-fx-background-color: transparent;");
        });
    }

    @FXML
    public void handleUpdateProfil() {
        Stage stage = (Stage) updatePr.getScene().getWindow();
        stage.close();
        showFrame("UpdateClient.fxml");
    }
    private void showFrame(String fxml){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleLogoutButton(ActionEvent event) {
        Stage stage = (Stage) logoutBut.getScene().getWindow();
        stage.close();
        showFrame("Login.fxml");
    }


}
