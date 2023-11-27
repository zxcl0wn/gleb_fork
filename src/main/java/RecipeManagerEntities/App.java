package RecipeManagerEntities;


import java.sql.SQLException;
import java.util.List;

public class App {

    public static void main(String[] args) throws SQLException {
        RecipeIngredient ttr = RecipeIngredient.addToDBAndGet(33, 33, 9191919);
        System.out.println(ttr);
        System.out.println("---------");

        List<RecipeIngredient> rs1 = RecipeIngredient.getAllRecipesIngredientsList();

       for (RecipeIngredient r : rs1) {
           System.out.println(r.toString());

       }

        System.out.println("---------");

        List<Ingredient> i1 = Ingredient.getAllIngredientsList();

        for (Ingredient i : i1) {
            System.out.println(i.toString());

        }


    }
}