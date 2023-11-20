package DatabaseAPI;
import java.sql.*;

abstract class IngredientDBAbstract extends DB_BaseAbstract {
    public IngredientDBAbstract(String tableName) {
        super(tableName);
    }

    public abstract void create(String name, double calories, double protein, double fats, double carbs);

    public abstract void update(int id, String name, double calories, double protein, double fats, double carbs);
}

public class IngredientDB extends IngredientDBAbstract {
    public IngredientDB() {
        super("Ingredients");
    }

    public void createTableIfNotExists() {
        try (Connection connection = this.getConnection()) {
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
        try (Connection connection = this.getConnection()) {
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
//            e.printStackTrace();
        }

    }
//
//    public ResultSet read(int id) {
//        try (Connection connection = this.getConnection()) {
//            String selectQuery = String.format("SELECT * FROM %s WHERE id = ?;", tableName);
//            PreparedStatement pstmt = connection.prepareStatement(selectQuery);
//            pstmt.setInt(1, id);
//            return pstmt.executeQuery();
//        } catch (SQLException e) {
//            System.out.println("Error connecting to PostgreSQL database:");
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public ResultSet readAll(){
//        try (Connection connection = this.getConnection()) {
//            String selectQuery = String.format("SELECT * FROM %s;", tableName);
//            Statement stmt = connection.createStatement();
//            return stmt.executeQuery(selectQuery);
//        } catch (SQLException e) {
//            System.out.println("Error connecting to PostgreSQL database:");
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public void delete(int id){
//        try (Connection connection = this.getConnection()) {
//            String deleteQuery = String.format("DELETE FROM %s WHERE id = ?;", tableName);
//            PreparedStatement pstmt = connection.prepareStatement(deleteQuery);
//            pstmt.setInt(1, id);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println("Error connecting to PostgreSQL database:");
//            e.printStackTrace();
//        }
//    }
    @Override
    public void update(int id, String name, double calories, double protein, double fats, double carbs) {
        try (Connection connection = this.getConnection()) {
            String updateQuery = String.format(
                    "UPDATE %s SET name = ?, calories = ?, protein = ?, fats = ?, carbs = ? WHERE id = ?",
                    tableName
            );
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
