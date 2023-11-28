package RecipeManagerEntities;


import DatabaseAPI.CategoriesDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class App {

    public static void main(String[] args) throws SQLException {

//        List<Recipe> r1 = Recipe.getAllRecipes();
//
//        for (Recipe r : r1){
//            System.out.println(r.toString());
//            System.out.println("---------");
//        }
//
//        System.out.println("()()()()()()()");
//
//        Recipe res1 = Recipe.getRecipeById(9);
//        System.out.println(res1);
//
        System.out.println(Category.getAllCategoriesList());
//        System.out.println(res1.getProteinOfRecipe());
        System.out.println(IngredientWithQuantity.getIngredientsWithQuantityByRecipeId(6));

//        ShoppingCart.addIngredientsToShoppingCartByRecipe(Recipe.getRecipeById(6));
//        System.out.println(ShoppingCart.getIngredientsMap());




    }
}