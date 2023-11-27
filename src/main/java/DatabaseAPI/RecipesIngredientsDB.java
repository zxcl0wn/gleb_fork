package DatabaseAPI;


import java.sql.*;


abstract class RecipesIngredientsDBAbstract extends DB_BaseAbstract {
    public RecipesIngredientsDBAbstract(String tableName) {
        super(tableName);
    }

    public abstract void create(int recipe_id, int ingredient_id, double quantity_of_ingredient);

    public abstract void update(int id, double quantity_of_ingredient);

    public abstract ResultSet readByRecipeId(int recipe_id);

    public abstract ResultSet readByIngredientId(int ingredient_id);

    public abstract ResultSet readByRecipeIdAndIngredientId(int recipe_id, int ingredient_id);

}

public class RecipesIngredientsDB extends RecipesIngredientsDBAbstract {
    public RecipesIngredientsDB() {
        super("RecipesIngredients");
    }

    public void createTableIfNotExists() {
        try (Connection connection = this.getConnection()) {
            String createTableQuery = String.format("CREATE TABLE IF NOT EXISTS %s (" +
                    "id SERIAL PRIMARY KEY," +
                    "recipe_id INTEGER REFERENCES Recipe (id) ON DELETE CASCADE," +
                    "ingredient_id INTEGER REFERENCES Ingredients (id) ON DELETE CASCADE," +
                    "quantity_of_ingredient DOUBLE PRECISION" +
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

        IngredientDB ingredient_db = new IngredientDB();
        ingredient_db.initTable();

        this.createTableIfNotExists();
    }

    public void create(int recipe_id, int ingredient_id, double quantity_of_ingredient) {
        try (Connection connection = this.getConnection()) {
            String insertQuery = String.format(
                    "INSERT INTO %s (recipe_id, ingredient_id, quantity_of_ingredient) VALUES (?, ?, ?);",
                    this.tableName
            );
            PreparedStatement pstmt = connection.prepareStatement(insertQuery);
            pstmt.setInt(1, recipe_id);
            pstmt.setInt(2, ingredient_id);
            pstmt.setDouble(3, quantity_of_ingredient);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }

    }

    public void update(int id, double quantity_of_ingredient) {
        try (Connection connection = this.getConnection()) {
            String updateQuery = String.format(
                    "UPDATE %s SET quantity_of_ingredient = ? WHERE id = ?;",
                    this.tableName
            );
            PreparedStatement pstmt = connection.prepareStatement(updateQuery);
            pstmt.setDouble(1, quantity_of_ingredient);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet readByRecipeId(int recipe_id) {
        try (Connection connection = this.getConnection()) {
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

    @Override
    public ResultSet readByIngredientId(int ingredient_id) {
        try (Connection connection = this.getConnection()) {
            String selectQuery = String.format("SELECT * FROM %s WHERE ingredient_id = ?", this.tableName);
            PreparedStatement pstmt = connection.prepareStatement(selectQuery);
            pstmt.setInt(1, ingredient_id);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultSet readByRecipeIdAndIngredientId(int recipe_id, int ingredient_id) {
        try (Connection connection = this.getConnection()) {
            String selectQuery = String.format("SELECT * FROM %s WHERE recipe_id = ? AND ingredient_id = ?", this.tableName);
            PreparedStatement pstmt = connection.prepareStatement(selectQuery);
            pstmt.setInt(1, recipe_id);
            pstmt.setInt(2, ingredient_id);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error connecting to PostgreSQL database:");
            e.printStackTrace();
        }
        return null;
    }

}
