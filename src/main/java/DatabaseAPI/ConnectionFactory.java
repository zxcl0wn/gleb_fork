package DatabaseAPI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//import java.sql.*;


public interface ConnectionFactory {
    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DBConfig.url, DBConfig.username, DBConfig.password);
    }

}

class ConnectionSingleton implements ConnectionFactory {
    private static Connection connection;

    private ConnectionSingleton() throws SQLException {
        connection = DriverManager.getConnection(DBConfig.url, DBConfig.username, DBConfig.password);
    }

    public static synchronized Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            new ConnectionSingleton();
        }
        return connection;
    }
}


