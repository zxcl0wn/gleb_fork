package DatabaseAPI;

import java.sql.*;


interface DB_GetConnection {
    default Connection getConnection() throws SQLException {
        return ConnectionSingleton.getConnection();
    }
}

interface DB_InitTableInterface {
    void initTable();
}

interface DB_CreateTableInterface {
    void createTableIfNotExists();
}

interface DB_ReadInterface {
    ResultSet read(int id);

    ResultSet readAll();
}

interface DB_DeleteInterface {
    void delete(int id);
}

interface DB_BaseOptions extends DB_GetConnection, DB_CreateTableInterface, DB_InitTableInterface,
    DB_ReadInterface, DB_DeleteInterface {}


public abstract class DB_BaseAbstract implements DB_BaseOptions {
    protected String tableName;

    public DB_BaseAbstract(String tableName){
        this.tableName = tableName;
    }

    public int countLines() {
        try {
            Connection connection = this.getConnection();
            String query = String.format("SELECT COUNT(*) FROM %s;", this.tableName);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }
        return -1;
    }

    public ResultSet read(int id) {
        try {
            Connection connection = this.getConnection();
            String selectQuery = String.format("SELECT * FROM %s WHERE id = ?", this.tableName);
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
        try {
            Connection connection = this.getConnection();
            String selectQuery = String.format("SELECT * FROM %s", this.tableName);
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(selectQuery);
        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }
        return null;
    }

    public void delete(int id){
        try {
            Connection connection = this.getConnection();
            String deleteQuery = String.format("DELETE FROM %s WHERE id = ?", this.tableName);
            PreparedStatement pstmt = connection.prepareStatement(deleteQuery);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }
    }
}