package RecipeManagerEntities;

import java.sql.SQLException;
import java.util.List;
import java.util.LinkedList;
import java.util.Objects;

public class IngredientWithQuantity{
    private int recipe_id;
    private Ingredient ingredient;
    private double quantity;

    private IngredientWithQuantity(int recipe_id, Ingredient ingredient, double quantity) {
        this.recipe_id = recipe_id;
        this.ingredient = ingredient;
        this.quantity = quantity;
    }

    public static List<IngredientWithQuantity> getIngredientsWithQuantityByRecipeId(int recipe_id) throws SQLException {
        List<IngredientWithQuantity> list_of_ingredients_with_quantity = new LinkedList<>();
        List<RecipeIngredient> list_of_recipe_ingredients = RecipeIngredient.getRecipeIngredientsByRecipeIdList(recipe_id);

        for (RecipeIngredient recipe_ingredient : list_of_recipe_ingredients) {
            list_of_ingredients_with_quantity.add(
                    new IngredientWithQuantity(
                            recipe_id,
                            Ingredient.getIngredientById(recipe_ingredient.getIngredientId()),
                            recipe_ingredient.getQuantityOfIngredient()
                    )
            );
        }
        return list_of_ingredients_with_quantity;
    }

    public IngredientWithQuantity getIngredientsWithQuantityByRecipeIdAndIngredientId(int recipe_id, int ingredient_id) throws SQLException {
        RecipeIngredient recipe_ingredient = RecipeIngredient.getRecipeIngredientByRecipeIdAndIngredientId(recipe_id, ingredient_id);
        if (Objects.isNull(recipe_ingredient)) return null;

        return new IngredientWithQuantity(
                recipe_id,
                Ingredient.getIngredientById(recipe_ingredient.getIngredientId()),
                recipe_ingredient.getQuantityOfIngredient()
        );
    }

    public boolean delete() throws SQLException {
//      удаляем запись из бд RecipeIngredients
        RecipeIngredient recipe_ingredient = RecipeIngredient.getRecipeIngredientByRecipeIdAndIngredientId(recipe_id, ingredient.getId());
        if (Objects.isNull(recipe_ingredient)) return false;
        recipe_ingredient.delete();
        recipe_id = -1;
        ingredient = null;
        quantity = -1;
        return true;
    }

    private boolean updateQuantity(double new_quantity) throws SQLException {
        RecipeIngredient recipe_ingredient = RecipeIngredient.getRecipeIngredientByRecipeIdAndIngredientId(recipe_id, ingredient.getId());
        if (Objects.isNull(recipe_ingredient)) return false;
        recipe_ingredient.setQuantityOfIngredient(new_quantity);
        return true;
    }

    @Override
    public String toString() {
        return "IngredientWithQuantity{" +
                "recipe_id=" + recipe_id +
                ", ingredient=" + ingredient +
                ", quantity=" + quantity +
                '}';
    }

    public int getRecipeId() {
        return recipe_id;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public double getQuantity() {
        return quantity;
    }

    public boolean setQuantity(double quantity) throws SQLException {
        boolean success = this.updateQuantity(quantity);
        if (!success) return false;
        this.quantity = quantity;
        return true;
    }

    public double getCaloriesOfIngredientWithQuantity() {
        return this.quantity * this.ingredient.getCalories() / 100;
    }

    public double getProteinOfIngredientWithQuantity() {
        return this.quantity * this.ingredient.getProtein() / 100;
    }

    public double getFatsOfIngredientWithQuantity() {
        return this.quantity * this.ingredient.getFats() / 100;
    }

    public double getCarbsOfIngredientWithQuantity() {
        return this.quantity * this.ingredient.getCarbs() / 100;
    }

}