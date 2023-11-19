package DatabaseAPI;
import java.sql.*;

interface DB_Ingredient_Interface extends DB_BaseOptions{
    public void create(String name, double calories, double protein, double fats, double carbs);
    public void update(int id, String name, double calories, double protein, double fats, double carbs);
}

abstract class IngredientDBAbstract extends DB_BaseAbstract {
    public IngredientDBAbstract(String tableName) {
        super(tableName);
    }
}

class test extends IngredientDBAbstract {

    public test(String tableName) {
        super(tableName);
    }

    @Override
    public void initTable() {

    }

    @Override
    public void createTableIfNotExists() {

    }
}

public class IngredientDB implements DB_Ingredient_Interface {
    public void createTableIfNotExists() {
        try (Connection connection = this.getConnection()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Ingredients (" +
                    "id SERIAL PRIMARY KEY," +
                    "name TEXT," +
                    "calories DOUBLE PRECISION," +
                    "protein DOUBLE PRECISION," +
                    "fats DOUBLE PRECISION," +
                    "carbs DOUBLE PRECISION" +
                    ")";

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

    public void create(String name, double calories, double protein, double fats, double carbs) {
        try (Connection connection = this.getConnection()) {
            String insertQuery = "INSERT INTO Ingredients (name, calories, protein, fats, carbs) VALUES (?, ?, ?, ?, ?)";
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

    public void update(int id, String name, double calories, double protein, double fats, double carbs) {
        try (Connection connection = this.getConnection()) {
            String updateQuery = "UPDATE Ingredients SET name = ?, calories = ?, protein = ?, fats = ?, carbs = ? WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(updateQuery);
            pstmt.setString(1, name);
            pstmt.setDouble(2, calories);
            pstmt.setDouble(3, protein);
            pstmt.setDouble(4, fats);
            pstmt.setDouble(5, carbs);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }
    }
}
