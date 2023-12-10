package RecipeManagerEntities;


import DatabaseAPI.CategoriesDB;
import DatabaseAPI.IngredientDB;
import Parser100menu.AllCPFC;
import Parser100menu.RecipeParser;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class App {

    public static void main(String[] args) throws SQLException {
//
//        System.out.println(Category.getAllCategoriesList());
//
//        System.out.println("123");
//
//        System.out.println(Ingredient.getAllIngredientsList());
//
//
//        Recipe res1 = Recipe.getRecipeById(1);
//
//        Ingredient ing1 = Ingredient.getIngredientById(2);
//
//        res1.getIngredientsWithQuantity().getLast().setQuantity(209);

//        System.out.println(res1.addNewIngredientWithQuantity(ing1, 1000000));
//        System.out.println(ShoppingCart.getIngredientsMap());


//        List<Ingredient> all_ing = Ingredient.getAllIngredientsList();
//        System.out.println(Ingredient.getAllIngredientsList());
//        System.out.println(Ingredient.getAllIngredientsList());



//        AllCPFC all_ingred = new AllCPFC();
//        Vector<Map> table_all_ingred;
//
//        try {
//            table_all_ingred = all_ingred.table_CPFC();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return;
//        }
//
//        for (Map<String, String> ingredientData : table_all_ingred) {
//            String name = ingredientData.get("name");
//            double calories = Double.parseDouble(ingredientData.get("calories"));
//            double protein = Double.parseDouble(ingredientData.get("proteins"));
//            double fats = Double.parseDouble(ingredientData.get("fats"));
//            double carbs = Double.parseDouble(ingredientData.get("carbohydrates"));
//            Ingredient zxc = Ingredient.addToDBAndGet(name, calories, protein, fats, carbs);
//            System.out.println(zxc);
//        }

    }
}