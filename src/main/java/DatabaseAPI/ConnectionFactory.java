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
