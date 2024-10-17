package com.example.taxi;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpAs {

    @FXML
    private RadioButton driverRadioButton;

    @FXML
    private RadioButton clientRadioButton;

    @FXML
    private Button joinButton;

    private ToggleGroup toggleGroup;

    @FXML
    private void initialize() {
        toggleGroup = new ToggleGroup();
        driverRadioButton.setToggleGroup(toggleGroup);
        clientRadioButton.setToggleGroup(toggleGroup);
    }

    @FXML
    private void handleRadioButtonAction() {
        if (toggleGroup.getSelectedToggle() == driverRadioButton) {
            joinButton.setText("Join as driver");
        } else if (toggleGroup.getSelectedToggle() == clientRadioButton) {
            joinButton.setText("Join as client");
        }
    }
    @FXML
    private void JoinButtonAction(){
        try{
            if(toggleGroup.getSelectedToggle()==driverRadioButton){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("registerDriver.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

                Stage signUpAsStage = (Stage) joinButton.getScene().getWindow();
                signUpAsStage.close();
            } else if (toggleGroup.getSelectedToggle()==clientRadioButton) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("registerClient.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();

                Stage signUpAsStage = (Stage) joinButton.getScene().getWindow();
                signUpAsStage.close();


            }

        }catch (IOException e){
            e.printStackTrace();
        }

    }

}
