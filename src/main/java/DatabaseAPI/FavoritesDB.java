package DatabaseAPI;


import java.sql.*;


abstract class FavoritesDBAbstract extends DB_BaseAbstract {
    public FavoritesDBAbstract(String tableName) {
        super(tableName);
    }

    public abstract void create(int recipe_id);

    public abstract void update();
}

public class FavoritesDB extends FavoritesDBAbstract {
    public FavoritesDB() {
        super("Favorites");
    }

    public void createTableIfNotExists() {
        try (Connection connection = this.getConnection()) {
            String createTableQuery = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                    "id SERIAL PRIMARY KEY," +
                    "recipe_id INTEGER UNIQUE REFERENCES Recipe (id) ON DELETE CASCADE" +
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

    public void create(int recipe_id) {
        try (Connection connection = this.getConnection()) {
            String insertQuery = String.format(
                    "INSERT INTO %s (recipe_id) VALUES (?);",
                    this.tableName
            );

            PreparedStatement pstmt = connection.prepareStatement(insertQuery);
            pstmt.setInt(1, recipe_id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }

    }

    public void update() {}

}
