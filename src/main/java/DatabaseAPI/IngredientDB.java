package DatabaseAPI;
import java.sql.*;

abstract class IngredientDBAbstract extends DB_BaseAbstract {
    public IngredientDBAbstract(String tableName) {
        super(tableName);
    }

    public abstract void create(String name, double calories, double protein, double fats, double carbs);

    public abstract boolean update(int id, String name, double calories, double protein, double fats, double carbs);

    public abstract ResultSet readByName(String name);
}

public class IngredientDB extends IngredientDBAbstract {
    public IngredientDB() {
        super("Ingredients");
    }

    public void createTableIfNotExists() {
        try {
            Connection connection = this.getConnection();
            String createTableQuery = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                    "id SERIAL PRIMARY KEY," +
                    "name TEXT UNIQUE," +
                    "calories DOUBLE PRECISION," +
                    "protein DOUBLE PRECISION," +
                    "fats DOUBLE PRECISION," +
                    "carbs DOUBLE PRECISION" +
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
    public void create(String name, double calories, double protein, double fats, double carbs) {
        try {
            Connection connection = this.getConnection();
            String insertQuery = String.format(
                "INSERT INTO %s (name, calories, protein, fats, carbs) VALUES (?, ?, ?, ?, ?);",
                this.tableName
            );

            PreparedStatement pstmt = connection.prepareStatement(insertQuery);
            pstmt.setString(1, name);
            pstmt.setDouble(2, calories);
            pstmt.setDouble(3, protein);
            pstmt.setDouble(4, fats);
            pstmt.setDouble(5, carbs);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }

    }

    @Override
    public boolean update(int id, String name, double calories, double protein, double fats, double carbs) {
        try {
            Connection connection = this.getConnection();
            String updateQuery = String.format(
                    "UPDATE %s SET name = ?, calories = ?, protein = ?, fats = ?, carbs = ? WHERE id = ?;",
                    this.tableName
            );
            PreparedStatement pstmt = connection.prepareStatement(updateQuery);
            pstmt.setString(1, name);
            pstmt.setDouble(2, calories);
            pstmt.setDouble(3, protein);
            pstmt.setDouble(4, fats);
            pstmt.setDouble(5, carbs);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ResultSet readByName(String name) {
        try {
            Connection connection = this.getConnection();
            String selectQuery = String.format("SELECT * FROM %s WHERE name = ?", this.tableName);
            PreparedStatement pstmt = connection.prepareStatement(selectQuery);
            pstmt.setString(1, name);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }
        return null;
    }

}
