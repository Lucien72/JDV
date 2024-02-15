package jdv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryExecutor {

    private final ConnectionDB databaseConnector;

    public QueryExecutor(ConnectionDB databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public ProfilUtilisateur getProfilUtilisateur(String username) {
        String query = "SELECT * FROM User WHERE name = '" + username + "'";
        try (Connection connection = databaseConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            // Traiter les résultats de la requête
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String password = resultSet.getString("password");
                int cellAlive = resultSet.getInt("cell_alive");

                return new ProfilUtilisateur(id, name, password, cellAlive);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void insertUtilisateur(ProfilUtilisateur utilisateur) {
        String query = "INSERT INTO User (name, password, cell_alive) VALUES (?, ?, ?)";
        try (Connection connection = databaseConnector.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Set parameters
            preparedStatement.setString(1, utilisateur.getNomUtilisateur());
            preparedStatement.setString(2, utilisateur.getPassword());
            preparedStatement.setInt(3, utilisateur.getCellAlive());
            
            // Execute the query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}