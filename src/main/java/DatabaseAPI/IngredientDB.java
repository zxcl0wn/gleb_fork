package DatabaseAPI;
import java.sql.*;

public class IngredientDB {
    private Connection conn;

    public IngredientDB(String url, String username, String password) throws SQLException {
        this.conn = DriverManager.getConnection(url, username, password);
    }

    public void createTableIfNotExists() throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS Ingredients (" +
                "id SERIAL PRIMARY KEY," +
                "name TEXT," +
                "calories DOUBLE PRECISION," +
                "protein DOUBLE PRECISION," +
                "fats DOUBLE PRECISION," +
                "carbs DOUBLE PRECISION" +
                ")";

        Statement stmt = conn.createStatement();
        stmt.executeUpdate(createTableQuery);
    }

    public void create(String name, double calories, double protein, double fats, double carbs) throws SQLException {
        String insertQuery = "INSERT INTO Ingredients (name, calories, protein, fats, carbs) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(insertQuery);
        pstmt.setString(1, name);
        pstmt.setDouble(2, calories);
        pstmt.setDouble(3, protein);
        pstmt.setDouble(4, fats);
        pstmt.setDouble(5, carbs);
        pstmt.executeUpdate();
    }

    public ResultSet read(int id) throws SQLException {
        String selectQuery = "SELECT * FROM Ingredients WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(selectQuery);
        pstmt.setInt(1, id);
        return pstmt.executeQuery();
    }

    public void update(int id, String name, double calories, double protein, double fats, double carbs) throws SQLException {
        String updateQuery = "UPDATE Ingredients SET name = ?, calories = ?, protein = ?, fats = ?, carbs = ? WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(updateQuery);
        pstmt.setString(1, name);
        pstmt.setDouble(2, calories);
        pstmt.setDouble(3, protein);
        pstmt.setDouble(4, fats);
        pstmt.setDouble(5, carbs);
        pstmt.setInt(6, id);
        pstmt.executeUpdate();
    }

    public void delete(int id) throws SQLException {
        String deleteQuery = "DELETE FROM Ingredients WHERE id = ?";
        PreparedStatement pstmt = conn.prepareStatement(deleteQuery);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }

    public ResultSet readAll() throws SQLException {
        String selectQuery = "SELECT * FROM Ingredients";
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(selectQuery);
    }
    private int id;
    private String name;
    private double calories;
    private double protein;
    private double fats;
    private double carbs;
}
