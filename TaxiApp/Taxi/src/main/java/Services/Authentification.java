package Services;

import com.example.taxi.BdConnexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authentification {
    private BdConnexion bdConnexion;
    private static String loggedInEmail;
    public  static  int id;

    public Authentification() {
        this.bdConnexion = new BdConnexion();
    }

    public boolean authenticateUser(String email, String password) {
        String query = "SELECT * FROM utilisateur WHERE email = ? AND password = ?";
        try (PreparedStatement preparedStatement = bdConnexion.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                loggedInEmail = email;
                id=resultSet.getInt("id");
                System.out.println("Logged-in : " + id);

                System.out.println("Logged-in email: " + loggedInEmail);
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getUserType(String email, String password) {
        String userType = null;
        if (authenticateUser(email, password)) {
            userType = getUserRoleFromDatabase(email);
        }

        return userType;
    }

    private String getUserRoleFromDatabase(String email) {
        String query = "SELECT role FROM utilisateur WHERE email = ?";

        try (PreparedStatement preparedStatement = bdConnexion.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Static method to retrieve the logged-in driver's email
    public static String getLoggedInDriverEmail() {
        return loggedInEmail;
    }
    public static int getLoggedInDriverId() {
        return id;
    }
}

