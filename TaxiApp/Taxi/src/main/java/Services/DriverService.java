package Services;

import Model.Driver;
import com.example.taxi.BdConnexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DriverService {
    private BdConnexion bdConnexion;



    public DriverService() {
        this.bdConnexion = new BdConnexion();
    }

    public DriverService(Authentification authentification) {
        this.bdConnexion = new BdConnexion();

    }

    public void registerDriver(Driver driver) {
        String utilisateurQuery = "INSERT INTO utilisateur (fullname, telephone, role, email, password, datenaissance, sexe, city) VALUES (?, ?, 'driver', ?, ?, ?, ?, ?)";

        try (PreparedStatement utilisateurStatement = bdConnexion.getConnection().prepareStatement(utilisateurQuery, Statement.RETURN_GENERATED_KEYS)) {
            utilisateurStatement.setString(1, driver.getNom());
            utilisateurStatement.setString(2, driver.getTelephone());
            utilisateurStatement.setString(3, driver.getEmail());
            utilisateurStatement.setString(4, driver.getPassword());
            utilisateurStatement.setString(5, String.valueOf(driver.getDateDeNaissance()));
            utilisateurStatement.setString(6, driver.getSexe());
            utilisateurStatement.setString(7, driver.getCity());

            int affectedRows = utilisateurStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = utilisateurStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);

                        String driverQuery = "INSERT INTO driver (userId, cartype) VALUES (?, ?)";

                        try (PreparedStatement driverStatement = bdConnexion.getConnection().prepareStatement(driverQuery)) {
                            driverStatement.setInt(1, userId);
                            driverStatement.setString(2, driver.getCarType());

                            driverStatement.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Driver> getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        String query = "SELECT * FROM utilisateur JOIN driver ON utilisateur.id = driver.userId";

        try (PreparedStatement preparedStatement = bdConnexion.getConnection().prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Driver driver = new Driver();
                driver.setNom(resultSet.getString("fullname"));
                driver.setCarType(resultSet.getString("cartype"));
                driver.setId(resultSet.getInt("id"));


                drivers.add(driver);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return drivers;
    }

    public Driver getDriver(int id) {
        Driver driver=null;
        //String query = "SELECT * FROM utilisateur WHERE role = 'driver' and id=?";
        String query = "SELECT * FROM utilisateur JOIN driver ON utilisateur.id = driver.userId WHERE userId=? and role ='driver'";
        try (PreparedStatement preparedStatement = bdConnexion.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                driver = new Driver();
                driver.setNom(resultSet.getString("fullname"));
                driver.setTelephone(resultSet.getString("telephone"));
                driver.setEmail(resultSet.getString("email"));
                driver.setPassword(resultSet.getString("password"));
                driver.setCity(resultSet.getString("city"));
                driver.setCarType(resultSet.getString("carType"));


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return driver;
    }




    public Driver updateDriver(Driver driver) throws SQLException {
        Driver driver1=null;
        String updateUtilisateurQuery = "UPDATE utilisateur SET fullname=?, telephone=?, email=?, password=?, city=? WHERE id=?";

        try (Connection connection = bdConnexion.getConnection()) {
            connection.setAutoCommit(false);  // Disable auto-commit to manage transactions

            try (PreparedStatement updateUtilisateurStatement = connection.prepareStatement(updateUtilisateurQuery)) {
                // Update utilisateur table
                updateUtilisateurStatement.setString(1, driver.getNom());
                updateUtilisateurStatement.setString(2, driver.getTelephone());
                updateUtilisateurStatement.setString(3, driver.getEmail());
                updateUtilisateurStatement.setString(4, driver.getPassword());
                updateUtilisateurStatement.setString(5, driver.getCity());
                updateUtilisateurStatement.setInt(6,driver.getId());
                updateUtilisateurStatement.executeUpdate();

                connection.commit();  // Commit the transaction
            } catch (SQLException e) {
                connection.rollback();  // Rollback if there's an exception
                throw e;
            }
        } catch (SQLException e) {
            throw e;
        }
        return driver1;
    }






}