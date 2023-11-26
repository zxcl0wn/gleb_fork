package RecipeManagerEntities;


import java.sql.SQLException;
import java.util.List;

public class App {

    public static void main(String[] args) throws SQLException {
       List<Ingredient> list_of_ingredients = Ingredient.getIngredientsByResultSet(Ingredient.getAllIngredientsResultSet());

       System.out.println(list_of_ingredients.size());

        for (Ingredient i1 : list_of_ingredients) {
            System.out.println(i1.toString());
        }

       System.out.println("ok");

        Ingredient i2 = Ingredient.addToDBAndGet("Томат", 20, 3, 5, 1);
        System.out.println(i2.toString());

        System.out.println("ok2");

        list_of_ingredients = Ingredient.getIngredientsByResultSet(Ingredient.getAllIngredientsResultSet());

        System.out.println(list_of_ingredients.size());

        for (Ingredient i1 : list_of_ingredients) {
            System.out.println(i1.toString());
        }

        System.out.println("new");

        RecipeStep recipe_step1 = RecipeStep.addToDBAndGet(4, "title", "img1");
        recipe_step1.text = "new text 1";
        recipe_step1.updateInDB();

        for (RecipeStep r1 : RecipeStep.getRecipeStepByResultSet(RecipeStep.getAllRecipeStepsResultSet())) {
            System.out.println(r1.toString());
        }

        RecipeStep recipe_step2 = RecipeStep.addToDBAndGet(4, "title", "img1");
        recipe_step2.text = "new text 234rtwd";

        for (RecipeStep r1 : RecipeStep.getRecipeStepByResultSet(RecipeStep.getAllRecipeStepsResultSet())) {
            System.out.println(r1.toString());
        }
        recipe_step2.updateInDB();



    }
}