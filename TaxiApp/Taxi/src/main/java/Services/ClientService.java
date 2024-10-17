package Services;

import Model.Client;
import com.example.taxi.BdConnexion;
import com.example.taxi.ClientInterface;
import com.example.taxi.Reservation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private BdConnexion bdConnexion;
    private int idcond;



    private static int idres;

    public ClientService() {
        this.bdConnexion = new BdConnexion();
    }

    public int getIdres() {
        return idres;
    }
    public void registerClient(Client client) {
        String utilisateurQuery = "INSERT INTO utilisateur (fullname, telephone, role, email, password, datenaissance, sexe, city) VALUES (?, ?, 'client', ?, ?, ?, ?, ?)";

        try (PreparedStatement utilisateurStatement = bdConnexion.getConnection().prepareStatement(utilisateurQuery, Statement.RETURN_GENERATED_KEYS)) {
            utilisateurStatement.setString(1, client.getNom());
            utilisateurStatement.setString(2, client.getTelephone());
            utilisateurStatement.setString(3, client.getEmail());
            utilisateurStatement.setString(4, client.getPassword());
            utilisateurStatement.setString(5, String.valueOf(client.getDateDeNaissance()));
            utilisateurStatement.setString(6, client.getSexe());
            utilisateurStatement.setString(7, client.getCity());

            int affectedRows = utilisateurStatement.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = utilisateurStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);

                        // Now insert into the client table using the generated user ID
                        String clientQuery = "INSERT INTO client (userId) VALUES (?)";

                        try (PreparedStatement clientStatement = bdConnexion.getConnection().prepareStatement(clientQuery)) {
                            clientStatement.setInt(1, userId);

                            clientStatement.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String query = "SELECT r.id,u.fullname,u.telephone FROM reservation r join utilisateur u on u.id=r.idClient WHERE r.idConducteur=?   and status='En Cours' and role='client'";
        idcond= Authentification.id;
        System.out.println(idcond);

        try (PreparedStatement preparedStatement = bdConnexion.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, idcond);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Client client = new Client();
                Reservation res=new Reservation();
                res.setId(resultSet.getInt("id"));
                client.setNom(resultSet.getString("fullname"));
                client.setTelephone(resultSet.getString("telephone"));

                clients.add(client);
                idres=res.getId();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }


    public Client getClient(int id) {
        Client client=null;
        String query = "SELECT * FROM utilisateur WHERE role = 'client' and id=?";

        try (PreparedStatement preparedStatement = bdConnexion.getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                client = new Client();
                client.setNom(resultSet.getString("fullname"));
                client.setEmail(resultSet.getString("email"));
                client.setTelephone(resultSet.getString("telephone"));
                client.setCity(resultSet.getString("city"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    public Client updateClient(Client currentClient) {
        Client client = null;
        String query = "UPDATE utilisateur " +
                "SET fullname = ?, email = ?, telephone = ?, city = ? " +
                "WHERE role = 'client' AND id = ?";

        try (PreparedStatement preparedStatement = bdConnexion.getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, currentClient.getNom());
            preparedStatement.setString(2, currentClient.getEmail());
            preparedStatement.setString(3, currentClient.getTelephone());
            preparedStatement.setString(4, currentClient.getCity());
            preparedStatement.setInt(5,currentClient.getId());

            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                client = currentClient;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }




}
