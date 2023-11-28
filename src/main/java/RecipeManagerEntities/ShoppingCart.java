package RecipeManagerEntities;

import DatabaseAPI.ShoppingCartDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ShoppingCart {
    private static ResultSet getShoppingCartIdsResultSet(){
        ShoppingCartDB shopping_cart_db = new ShoppingCartDB();
        return shopping_cart_db.readAll();
    }

    private static List<Integer> getShoppingCartIdsByResultSet(ResultSet rs) throws SQLException {
        List<Integer> list = new LinkedList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            int recipe_ingredient_id = rs.getInt("recipe_ingredient_id");
            list.add(recipe_ingredient_id);
        }
        return list;
    }

    private static List<Integer> getShoppingCartIds() throws SQLException {
        return getShoppingCartIdsByResultSet(getShoppingCartIdsResultSet());
    }

    public static void addIngredientsToShoppingCartByRecipe(Recipe recipe) throws SQLException {
        List<IngredientWithQuantity> ingredients_with_quantity = recipe.getIngredientsWithQuantity();
        ShoppingCartDB shopping_cart_db = new ShoppingCartDB();

        for (IngredientWithQuantity ingredient_with_quantity : ingredients_with_quantity) {
            RecipeIngredient recipe_ingredient = RecipeIngredient.getRecipeIngredientByRecipeIdAndIngredientId(
                    ingredient_with_quantity.getRecipeId(),
                    ingredient_with_quantity.getIngredient().getId()
            );
            if (Objects.isNull(recipe_ingredient)) continue;
            shopping_cart_db.create(recipe_ingredient.getId());
        }
    }

    public static Map<String, Double> getIngredientsMap () throws SQLException {
        Map<String, Double> map = new HashMap<>();
        List<Integer> ingredients = getShoppingCartIds();

        for (Integer i : ingredients) {
            RecipeIngredient recipe_ingredient = RecipeIngredient.getRecipeIngredientById(i);
            if (Objects.isNull(recipe_ingredient)) continue;
            if (Objects.isNull(Ingredient.getIngredientById(recipe_ingredient.getIngredientId()))) continue;

            String ingredient_name = Ingredient.getIngredientById(recipe_ingredient.getIngredientId()).getName();
            double ingredient_quantity = recipe_ingredient.getQuantityOfIngredient();

            if (map.containsKey(ingredient_name)) {
                double old_ingredient_quantity = map.get(ingredient_name);
                map.put(ingredient_name, ingredient_quantity + old_ingredient_quantity);
            } else {
                map.put(ingredient_name, ingredient_quantity);
            }
        }
        return map;


    }
}
