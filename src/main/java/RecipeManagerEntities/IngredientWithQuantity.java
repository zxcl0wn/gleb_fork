package RecipeManagerEntities;

import java.sql.SQLException;
import java.util.List;
import java.util.LinkedList;
import java.util.Objects;

public class IngredientWithQuantity{
    private final int recipe_id;
    private final Ingredient ingredient;
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

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}