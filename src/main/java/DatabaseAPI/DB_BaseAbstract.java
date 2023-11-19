package DatabaseAPI;

import java.sql.*;


interface DB_GetConnection {
    default Connection getConnection() throws SQLException {
        return ConnectionFactory.getConnection();
    }
}

interface DB_InitTableInterface {
    public void initTable();
}

interface DB_CreateTableInterface {
    public void createTableIfNotExists();
}

interface DB_ReadInterface {
    public ResultSet read(int id);

    public ResultSet readAll();
}

interface DB_DeleteInterface {
    public void delete(int id);
}

interface DB_BaseOptions extends DB_GetConnection, DB_CreateTableInterface, DB_InitTableInterface,
    DB_ReadInterface, DB_DeleteInterface {}


public abstract class DB_BaseAbstract implements DB_BaseOptions {
    private String tableName;

    public DB_BaseAbstract(String tableName){
        System.out.println("abstract1234");
        this.tableName = tableName;
        System.out.println(String.format("a: %s", this.tableName));
    }

    public ResultSet read(int id) {
        try (Connection connection = this.getConnection()) {
            String selectQuery = "SELECT * FROM Ingredients WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(selectQuery);
            pstmt.setInt(1, id);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet readAll(){
        try (Connection connection = this.getConnection()) {
            String selectQuery = "SELECT * FROM Ingredients";
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(selectQuery);
        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }
        return null;
    }

    public void delete(int id){
        try (Connection connection = this.getConnection()) {
            String deleteQuery = "DELETE FROM Ingredients WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(deleteQuery);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }
    }
}