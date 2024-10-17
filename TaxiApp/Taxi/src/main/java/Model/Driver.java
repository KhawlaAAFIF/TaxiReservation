package Model;

import java.time.LocalDate;

public class Driver extends Utilisateur {

    private String carType;


    public Driver(String fullName, String telephone, String email, String password, LocalDate datenaissance, String sexe, String city, String carType) {
        super(fullName, telephone, email, "driver", city, sexe, datenaissance, password);
        this.carType = carType;
    }
    public Driver() {
        super("", "", "", "", "", "", LocalDate.now(), "");
        this.carType = "";
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }



}
