package RecipeManagerEntities;

import DatabaseAPI.ShoppingCartDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

public class ShoppingCart {
    private static ResultSet getShoppingCartResultSet(){
        ShoppingCartDB shopping_cart_db = new ShoppingCartDB();
        return shopping_cart_db.readAll();
    }

    private static List<Integer> getShoppingCartRecipeIngredientIdByResultSet(ResultSet rs) throws SQLException {
        List<Integer> list = new LinkedList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            int recipe_ingredient_id = rs.getInt("recipe_ingredient_id");
            list.add(recipe_ingredient_id);
        }
        return list;
    }

    public boolean addIngredientsToShoppingCartByRecipe(Recipe recipe) {
        return true;
    }
}
