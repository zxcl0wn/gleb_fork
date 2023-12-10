package DatabaseAPI;

import java.sql.*;


abstract class ShoppingCartDBAbstract extends DB_BaseAbstract {
    public ShoppingCartDBAbstract(String tableName) {
        super(tableName);
    }

    public abstract void create(int recipe_ingredient_id);

    public abstract void update();

    public abstract void deleteByRecipeIngredientId(int recipe_ingredient_id);

}

public class ShoppingCartDB extends ShoppingCartDBAbstract {
    public ShoppingCartDB() {
        super("ShoppingCart");
    }

    public void createTableIfNotExists() {
        try {
            Connection connection = this.getConnection();
            String createTableQuery = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                    "id SERIAL PRIMARY KEY," +
                    "recipe_ingredient_id INTEGER UNIQUE REFERENCES RecipesIngredients (id) ON DELETE CASCADE" +
                    ");", this.tableName);

            Statement stmt = connection.createStatement();
            stmt.executeUpdate(createTableQuery);

        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }
    }

    public void initTable() {
        RecipesIngredientsDB recipes_ingredients_db =  new RecipesIngredientsDB();
        recipes_ingredients_db.initTable();

        this.createTableIfNotExists();
    }

    public void create(int recipe_ingredient_id) {
        try {
            Connection connection = this.getConnection();
            String insertQuery = String.format(
                    "INSERT INTO %s (recipe_ingredient_id) VALUES (?);",
                    this.tableName
            );

            PreparedStatement pstmt = connection.prepareStatement(insertQuery);
            pstmt.setInt(1, recipe_ingredient_id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }

    }

    public void update() {}

    public void deleteByRecipeIngredientId(int recipe_ingredient_id){
        try {
            Connection connection = this.getConnection();
            String deleteQuery = String.format("DELETE FROM %s WHERE recipe_ingredient_id = ?", this.tableName);
            PreparedStatement pstmt = connection.prepareStatement(deleteQuery);
            pstmt.setInt(1, recipe_ingredient_id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }
    }
}
