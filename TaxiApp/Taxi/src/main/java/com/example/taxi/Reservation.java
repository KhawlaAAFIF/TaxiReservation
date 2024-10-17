package com.example.taxi;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import Model.Client;
import Model.Driver;
import Services.Authentification;
import Services.ClientService;
import Services.DriverService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Reservation {
       
    @FXML
    private TextField lieu;
    @FXML
    private DatePicker date_depa;
    @FXML
    private DatePicker date_arri;
    @FXML
    private TextArea more_info;
    @FXML
    private TextField num_place;
    
    @FXML
    private TextField lieu_arriv;
    @FXML 
    private TextField prix;
    @FXML
    private Button back;

    @FXML
    private TableView<ObservableList<String>> listeReserv;

    @FXML
    private void initialize() {

        try {
			setupTableView();
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    private int idclient=Authentification.id;
    private int idcond=ClientInterface.DriverId;
    private int id;
    private DriverService driverService;
    private ClientService clientService;
    
    public Reservation(){
        listeReserv = new TableView<>();
       driverService=new DriverService();
       clientService =new ClientService();
    }
    public Reservation(int id){
        this.id=id;
    }

    public void setId(int id){
        this.id=id;
    }
    public int getId() {
        return id;
    }

    public void InsertRese() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(lieu.getText() == null && date_depa.getValue() ==null && num_place.getText() ==null && lieu_arriv.getText() ==null ||  date_arri.getValue() == null || prix.getText() ==null ) {
            myAlert(AlertType.WARNING, "Field required", "Fill all the fields");
        }
        if(Integer.parseInt(num_place.getText())>5){
            myAlert(AlertType.WARNING, "Number Places", "Number of Places must be under 5");

        }else{
        String formattedDate = currentDate.format(formatter);
        String datedepa=date_depa.getValue().format(formatter);
        String datearri=date_arri.getValue().format(formatter);
       BdConnexion bd=new BdConnexion();
       int r=bd.executerAction(String.format("insert into reservation values(null,%d,%d,'%s','%s','%s','%s','%s',%d,'%s',%f,'En Cours')",idclient,idcond,lieu.getText(),formattedDate,datedepa,datearri,more_info.getText(),Integer.parseInt(num_place.getText()),lieu_arriv.getText(),Double.parseDouble(prix.getText())));
       if (r > 0) {
           
           Client clt;
           Driver dr;
           clt = clientService.getClient(idclient);
           dr = driverService.getDriver(idcond);
           myAlert(AlertType.INFORMATION, "Reservation made", "Client: " + clt.getNom() + "\n Driver: " + dr.getNom() + "\nPrix:" + Double.parseDouble(prix.getText()));
           try {
               FXMLLoader loader = new FXMLLoader(getClass().getResource("MyReservations.fxml"));
               Parent root = loader.load();
               Scene scene = new Scene(root);
               Stage stage = (Stage) lieu.getScene().getWindow();
               stage.setScene(scene);
               stage.show();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }

       }
       
        
    }
    void myAlert(AlertType type,String title,String msg) {
       Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    
    public void setupTableView() throws SQLException {

        ObservableList<ObservableList<String>> data = fetchDataFromDatabase();
        listeReserv.setItems(data);

        String selectQuery = "SELECT r.id,r.lieu_depa,u.fullname,u.telephone,r.numbrplaces,r.prix,r.status FROM reservation r inner join utilisateur u on u.id=r.idConducteur where idClient=? ";
    	BdConnexion bd=new BdConnexion();
    	bd.getConnection().prepareStatement(selectQuery);
            try (PreparedStatement preparedStatement = bd.getConnection().prepareStatement(selectQuery)) {
            	preparedStatement.setInt(1, idclient);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    var metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    for (int i = 1; i <= columnCount; i++) {
                        final int columnIndex = i - 1;
                        TableColumn<ObservableList<String>, String> column = new TableColumn<>(metaData.getColumnName(i));
                        column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(columnIndex)));
                        listeReserv.getColumns().add(column);
                    }
                }
            }
         catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<ObservableList<String>> fetchDataFromDatabase() throws SQLException {
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        String selectQuery = "SELECT r.id,r.lieu_depa,u.fullname,u.telephone,r.numbrplaces,r.prix,r.status FROM reservation r inner join utilisateur u on u.id=r.idConducteur where idClient=? ";
    	BdConnexion bd=new BdConnexion();
    	bd.getConnection().prepareStatement(selectQuery);
            try (PreparedStatement preparedStatement = bd.getConnection().prepareStatement(selectQuery)) {
            	preparedStatement.setInt(1, idclient);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    // Get metadata to dynamically fetch column names
                    var metaData = resultSet.getMetaData();
                    int columnCount = metaData.getColumnCount();

                    while (resultSet.next()) {
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int i = 1; i <= columnCount; i++) {
                            row.add(resultSet.getString(i));
                        }
                        data.add(row);
                    }
                }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
    @FXML
    public void handleBackButton(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ClientInterface.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) back.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}