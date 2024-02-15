package jdv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private final String url;
    private final String user;
    private final String password;

    public ConnectionDB() {
        this.url = "jdbc:mariadb://localhost:3307/JDVBDD";
        this.user = "Lucien";
        this.password = "Lucien";
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}