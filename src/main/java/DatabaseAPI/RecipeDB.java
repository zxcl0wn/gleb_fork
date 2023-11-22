package DatabaseAPI;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;


abstract class RecipeDBAbstract extends DB_BaseAbstract {
    public RecipeDBAbstract(String tableName) {
        super(tableName);
    }

    public abstract void create(String name, int category, String img, int cooking_time, int difficulty_level);

    public abstract void update(int id, String name, int category, String img, int cooking_time, int difficulty_level);
}

public class RecipeDB extends RecipeDBAbstract {
    public RecipeDB() {
        super("Recipe");
    }

    public void createTableIfNotExists() {
        try (Connection connection = this.getConnection()) {
            String createTableQuery = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                    "id SERIAL PRIMARY KEY," +
                    "name TEXT UNIQUE," +
                    "category INTEGER REFERENCES Categories (id) ON DELETE CASCADE," +
                    "img TEXT," +
                    "cooking_time INTEGER," +
                    "difficulty_level INTEGER REFERENCES LevelsOfDifficulty (id) ON DELETE CASCADE" +
                    ");", this.tableName);

            Statement stmt = connection.createStatement();
            stmt.executeUpdate(createTableQuery);

        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }
    }

    public void initTable() {
        CategoriesDB category_db =  new CategoriesDB();
        category_db.initTable();

        LevelsOfDifficultyDB difficulty_level_db =  new LevelsOfDifficultyDB();
        difficulty_level_db.initTable();

        this.createTableIfNotExists();
    }

    @Override
    public void create(String name, int category, String img, int cooking_time, int difficulty_level) {
        try (Connection connection = this.getConnection()) {
            String insertQuery = String.format(
                    "INSERT INTO %s (name, category, img, cooking_time, difficulty_level) VALUES (?, ?, ?, ?, ?);",
                    this.tableName
            );

            PreparedStatement pstmt = connection.prepareStatement(insertQuery);
            pstmt.setString(1, name);
            pstmt.setInt(2, category);
            pstmt.setString(3, img);
            pstmt.setInt(4, cooking_time);
            pstmt.setInt(5, difficulty_level);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }

    }

    public void update(int id, String name, int category, String img, int cooking_time, int difficulty_level) {
        try (Connection connection = this.getConnection()) {
            String updateQuery = String.format(
        "UPDATE %s SET name = ?, category = ?, img = ?, cooking_time = ?, difficulty_level = ? WHERE id = ?;",
        this.tableName
            );
            PreparedStatement pstmt = connection.prepareStatement(updateQuery);
            pstmt.setString(1, name);
            pstmt.setInt(2, category);
            pstmt.setString(3, img);
            pstmt.setInt(4, cooking_time);
            pstmt.setInt(5, difficulty_level);
            pstmt.setInt(6, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }
    }

}
