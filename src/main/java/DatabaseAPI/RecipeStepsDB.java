package DatabaseAPI;

import java.sql.*;


abstract class RecipeStepsDBAbstract extends DB_BaseAbstract {
    public RecipeStepsDBAbstract(String tableName) {
        super(tableName);
    }

    public abstract void create(int recipe_id, String text, String img);

    public abstract void update(int id, int recipe_id, String text, String img);

    public abstract ResultSet readByRecipeId(int id);
}

public class RecipeStepsDB extends RecipeStepsDBAbstract {
    public RecipeStepsDB() {
        super("RecipeSteps");
    }

    public void createTableIfNotExists() {
        try (Connection connection = this.getConnection()) {
            String createTableQuery = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                    "id SERIAL PRIMARY KEY," +
                    "recipe_id INTEGER REFERENCES Recipe (id) ON DELETE CASCADE," +
                    "text TEXT," +
                    "img TEXT" +
                    ");", this.tableName);

            Statement stmt = connection.createStatement();
            stmt.executeUpdate(createTableQuery);

        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }
    }

    public void initTable() {
        RecipeDB recipe_db =  new RecipeDB();
        recipe_db.initTable();

        this.createTableIfNotExists();
    }

    public void create(int recipe_id, String text, String img) {
        try (Connection connection = this.getConnection()) {
            String insertQuery = String.format(
                    "INSERT INTO %s (recipe_id, text, img) VALUES (?, ?, ?);",
                    this.tableName
            );

            PreparedStatement pstmt = connection.prepareStatement(insertQuery);
            pstmt.setInt(1, recipe_id);
            pstmt.setString(2, text);
            pstmt.setString(3, img);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }

    }

    public void update(int id, int recipe_id, String text, String img) {
        try (Connection connection = this.getConnection()) {
            String updateQuery = String.format(
                    "UPDATE %s SET recipe_id = ?, text = ?, img = ? WHERE id = ?;",
                    this.tableName
            );
            PreparedStatement pstmt = connection.prepareStatement(updateQuery);
            pstmt.setInt(1, recipe_id);
            pstmt.setString(2, text);
            pstmt.setString(3, img);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet readByRecipeId(int id) {
        try (Connection connection = this.getConnection()) {
            String selectQuery = String.format("SELECT * FROM %s WHERE recipe_id = ?", this.tableName);
            PreparedStatement pstmt = connection.prepareStatement(selectQuery);
            pstmt.setInt(1, id);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }
        return null;
    }

}
