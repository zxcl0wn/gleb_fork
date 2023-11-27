package RecipeManagerEntities;

import DatabaseAPI.RecipesIngredientsDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RecipeIngredient {
    private int id;
    private int recipe_id;
    private int ingredient_id;
    private double quantityOfIngredient;

    private RecipeIngredient(int id, int recipe_id, int ingredient_id, double quantityOfIngredient) {
        this.id = id;
        this.recipe_id = recipe_id;
        this.ingredient_id = ingredient_id;
        this.quantityOfIngredient = quantityOfIngredient;
    }

    private static ResultSet getAllRecipesIngredientsResultSet(){
        RecipesIngredientsDB recipes_ingredients_db = new RecipesIngredientsDB();
        return recipes_ingredients_db.readAll();
    }

    private static ResultSet getRecipeIngredientByIdResultSet(int id){
        RecipesIngredientsDB recipes_ingredients_db = new RecipesIngredientsDB();
        return recipes_ingredients_db.read(id);
    }

    private static ResultSet getRecipeIngredientByRecipeIdAndIngredientIdResultSet(int recipe_id, int ingredient_id){
        RecipesIngredientsDB recipes_ingredients_db = new RecipesIngredientsDB();
        return recipes_ingredients_db.readByRecipeIdAndIngredientId(recipe_id, ingredient_id);
    }

    private static ResultSet getRecipeIngredientByRecipeIdResultSet(int recipe_id){
        RecipesIngredientsDB recipes_ingredients_db = new RecipesIngredientsDB();
        return recipes_ingredients_db.readByRecipeId(recipe_id);
    }

    private static ResultSet getRecipeIngredientByIngredientIdResultSet(int ingredient_id){
        RecipesIngredientsDB recipes_ingredients_db = new RecipesIngredientsDB();
        return recipes_ingredients_db.readByIngredientId(ingredient_id);
    }

    private static List<RecipeIngredient> getRecipesIngredientsByResultSet(ResultSet rs) throws SQLException {
        List<RecipeIngredient> list = new LinkedList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            int recipe_id = rs.getInt("recipe_id");
            int ingredient_id = rs.getInt("ingredient_id");
            double quantityOfIngredient = rs.getDouble("quantity_of_ingredient");
            list.add(new RecipeIngredient(id, recipe_id, ingredient_id, quantityOfIngredient));
        }
        return list;
    }

    public static List<RecipeIngredient> getAllRecipesIngredientsList() throws SQLException {
        return getRecipesIngredientsByResultSet(getAllRecipesIngredientsResultSet());
    }

    public static List<RecipeIngredient> getRecipesIngredientsByIngredientIdList(int ingredient_id) throws SQLException {
        return getRecipesIngredientsByResultSet(getRecipeIngredientByIngredientIdResultSet(ingredient_id));
    }

    public static List<RecipeIngredient> getRecipeIngredientsByRecipeIdList(int recipe_id) throws SQLException {
        return getRecipesIngredientsByResultSet(getRecipeIngredientByRecipeIdResultSet(recipe_id));
    }

    public static RecipeIngredient getRecipeIngredientsById(int id) throws SQLException {
        List<RecipeIngredient> ri1 = getRecipesIngredientsByResultSet(getRecipeIngredientByIdResultSet(id));
        if (ri1.isEmpty()) return null;
        return ri1.getFirst();
    }

    public static RecipeIngredient getRecipeIngredientsByRecipeIdAndIngredientId(int recipe_id, int ingredient_id) throws SQLException {
        List<RecipeIngredient> ri1 = getRecipesIngredientsByResultSet(getRecipeIngredientByRecipeIdAndIngredientIdResultSet(recipe_id, ingredient_id));
        if (ri1.isEmpty()) return null;
        return ri1.getFirst();
    }


    @Override
    public String toString() {
        return "RecipeIngredient{" +
                "id=" + id +
                ", recipe_id=" + recipe_id +
                ", ingredient_id=" + ingredient_id +
                ", quantityOfIngredient=" + quantityOfIngredient +
                '}';
    }

    public int getId() {
        return id;
    }

    public int getRecipeId() {
        return recipe_id;
    }

    public int getIngredientId() {
        return ingredient_id;
    }

    public double getQuantityOfIngredient() {
        return quantityOfIngredient;
    }

    public void setQuantityOfIngredient(double quantityOfIngredient) {
        this.quantityOfIngredient = quantityOfIngredient;
    }
}


