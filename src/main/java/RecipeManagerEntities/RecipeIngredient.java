package RecipeManagerEntities;

import DatabaseAPI.RecipesIngredientsDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


public class RecipeIngredient {
    private int id;
    private int recipe_id;
    private int ingredient_id;
    private double quantity_of_ingredient;

    private RecipeIngredient(int id, int recipe_id, int ingredient_id, double quantity_of_ingredient) {
        this.id = id;
        this.recipe_id = recipe_id;
        this.ingredient_id = ingredient_id;
        this.quantity_of_ingredient = quantity_of_ingredient;
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

    private static List<RecipeIngredient> getRecipesIngredientsByResultSet(ResultSet rs) {
        List<RecipeIngredient> list = new LinkedList<>();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                int recipe_id = rs.getInt("recipe_id");
                int ingredient_id = rs.getInt("ingredient_id");
                double quantity_of_ingredient = rs.getDouble("quantity_of_ingredient");
                list.add(new RecipeIngredient(id, recipe_id, ingredient_id, quantity_of_ingredient));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<RecipeIngredient> getAllRecipesIngredientsList() {
        return getRecipesIngredientsByResultSet(getAllRecipesIngredientsResultSet());
    }

    public static List<RecipeIngredient> getRecipesIngredientsByIngredientIdList(int ingredient_id) {
        return getRecipesIngredientsByResultSet(getRecipeIngredientByIngredientIdResultSet(ingredient_id));
    }

    public static List<RecipeIngredient> getRecipeIngredientsByRecipeIdList(int recipe_id) {
        return getRecipesIngredientsByResultSet(getRecipeIngredientByRecipeIdResultSet(recipe_id));
    }

    public static RecipeIngredient getRecipeIngredientById(int id) {
        List<RecipeIngredient> ri1 = getRecipesIngredientsByResultSet(getRecipeIngredientByIdResultSet(id));
        if (ri1.isEmpty()) return null;
        return ri1.getFirst();
    }

    public static RecipeIngredient getRecipeIngredientByRecipeIdAndIngredientId(int recipe_id, int ingredient_id) {
        List<RecipeIngredient> ri1 = getRecipesIngredientsByResultSet(getRecipeIngredientByRecipeIdAndIngredientIdResultSet(recipe_id, ingredient_id));
        if (ri1.isEmpty()) return null;
        return ri1.getFirst();
    }

    public static boolean checkIsInDB(int id) {
        RecipeIngredient ri1 = getRecipeIngredientById(id);
        return !Objects.isNull(ri1);
    }

    public static boolean checkIsInDB(int recipe_id, int ingredient_id) {
        RecipeIngredient ri1 = getRecipeIngredientByRecipeIdAndIngredientId(recipe_id, ingredient_id);
        return !Objects.isNull(ri1);
    }

    public static RecipeIngredient addToDBAndGet(int recipe_id, int ingredient_id, double quantity_of_ingredient) {
//      если такая запись есть | нет рецерта или ингредиента, то возвращает null
        if (checkIsInDB(recipe_id, ingredient_id)) return null;

        RecipesIngredientsDB recipes_ingredients_db = new RecipesIngredientsDB();
        boolean successful_operation = recipes_ingredients_db.create(recipe_id, ingredient_id, quantity_of_ingredient);
        if (!successful_operation) return null;
        return getAllRecipesIngredientsList().getLast();
    }

    private void updateInDB() {
        RecipesIngredientsDB recipes_ingredients_db = new RecipesIngredientsDB();
        recipes_ingredients_db.update(id, quantity_of_ingredient);
    }

    public void delete() {
//      удаляет  из бд
        RecipesIngredientsDB recipes_ingredients_db = new RecipesIngredientsDB();
        recipes_ingredients_db.delete(id);
        this.id = -1;
        this.recipe_id = -1;
        this.ingredient_id = -1;
        this.quantity_of_ingredient = -1;
    }

    @Override
    public String toString() {
        return "RecipeIngredient{" +
                "id=" + id +
                ", recipe_id=" + recipe_id +
                ", ingredient_id=" + ingredient_id +
                ", quantity_of_ingredient=" + quantity_of_ingredient +
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
        return quantity_of_ingredient;
    }

    public void setQuantityOfIngredient(double quantity_of_ingredient) {
        this.quantity_of_ingredient = quantity_of_ingredient;
        this.updateInDB();
    }
}


