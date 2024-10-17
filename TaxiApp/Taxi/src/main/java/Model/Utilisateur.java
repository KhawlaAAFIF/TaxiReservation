package Model;
import java.time.LocalDate;
public class Utilisateur {


    protected int id;
    protected String nom;
    protected String telephone;
    protected String email;
    protected String role;
    protected String city;
    protected String sexe;
    protected LocalDate dateDeNaissance;
    protected String password;


    public Utilisateur(String nom, String telephone, String email, String role, String city, String sexe, LocalDate dateDeNaissance, String password) {
        this.nom = nom;
        this.telephone = telephone;
        this.email = email;
        this.role = role;
        this.city = city;
        this.sexe = sexe;
        this.dateDeNaissance = dateDeNaissance;
        this.password = password;
    }

    public Utilisateur() {

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public LocalDate getDateDeNaissance() {
        return dateDeNaissance;
    }

    public void setDateDeNaissance(LocalDate dateDeNaissance) {
        this.dateDeNaissance = dateDeNaissance;
    }
}
