package Model;

import java.time.LocalDate;

public class Client extends Utilisateur {

    public Client(String fullName, String telephone, String role, String email, String password, String datenaissance, String sexe, String city) {
        super(fullName, telephone, email, role, city, sexe, LocalDate.parse(datenaissance), password);
    }

    public Client() {
        super();
    }
}
