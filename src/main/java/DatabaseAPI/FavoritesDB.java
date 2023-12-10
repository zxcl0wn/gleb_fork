package DatabaseAPI;


import java.sql.*;


abstract class FavoritesDBAbstract extends DB_BaseAbstract {
    public FavoritesDBAbstract(String tableName) {
        super(tableName);
    }

    public abstract boolean create(int recipe_id);

    public abstract void update();

    public abstract ResultSet readByRecipeId(int recipe_id);

}

public class FavoritesDB extends FavoritesDBAbstract {
    public FavoritesDB() {
        super("Favorites");
    }

    public void createTableIfNotExists() {
        try {
            Connection connection = this.getConnection();
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

    public boolean create(int recipe_id) {
        try {
            Connection connection = this.getConnection();
            String insertQuery = String.format(
                    "INSERT INTO %s (recipe_id) VALUES (?);",
                    this.tableName
            );
            PreparedStatement pstmt = connection.prepareStatement(insertQuery);
            pstmt.setInt(1, recipe_id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
            return false;
        }

    }

    public void update() {}

    @Override
    public ResultSet readByRecipeId(int recipe_id) {
        try {
            Connection connection = this.getConnection();
            String selectQuery = String.format("SELECT * FROM %s WHERE recipe_id = ?", this.tableName);
            PreparedStatement pstmt = connection.prepareStatement(selectQuery);
            pstmt.setInt(1, recipe_id);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }
        return null;
    }


}
