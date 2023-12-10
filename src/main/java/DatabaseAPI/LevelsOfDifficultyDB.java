package DatabaseAPI;

import java.sql.*;


abstract class LevelsOfDifficultyDBAbstract extends DB_BaseAbstract {
    public LevelsOfDifficultyDBAbstract(String tableName) {
        super(tableName);
    }

    public abstract void create(String name);

    public abstract void update(int id, String name);
}

public class LevelsOfDifficultyDB extends LevelsOfDifficultyDBAbstract {
    public LevelsOfDifficultyDB() {
        super("LevelsOfDifficulty");
    }

    public void createTableIfNotExists() {
        try {
            Connection connection = this.getConnection();
            String createTableQuery = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                    "id SERIAL PRIMARY KEY," +
                    "name TEXT UNIQUE" +
                    ");", this.tableName);

            Statement stmt = connection.createStatement();
            stmt.executeUpdate(createTableQuery);

        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }
    }

    public void initTable() {
        this.createTableIfNotExists();
    }

    @Override
    public void create(String name) {
        try {
            Connection connection = this.getConnection();
            String insertQuery = String.format(
                    "INSERT INTO %s (name) VALUES (?);",
                    this.tableName
            );

            PreparedStatement pstmt = connection.prepareStatement(insertQuery);
            pstmt.setString(1, name);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }

    }

    @Override
    public void update(int id, String name) {
        try {
            Connection connection = this.getConnection();
            String updateQuery = String.format(
                    "UPDATE %s SET name = ? WHERE id = ?;",
                    this.tableName
            );
            PreparedStatement pstmt = connection.prepareStatement(updateQuery);
            pstmt.setString(1, name);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }
    }

}
