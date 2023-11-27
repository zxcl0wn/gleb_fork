package RecipeManagerEntities;


import java.sql.SQLException;
import java.util.List;

public class App {

    public static void main(String[] args) throws SQLException {

        List<Recipe> r1 = Recipe.getAllRecipes();

        for (Recipe r : r1){
            System.out.println(r.toString());
            System.out.println("---------");
        }

        System.out.println("()()()()()()()");

        Recipe res1 = Recipe.getRecipeByName("Утка с грибами");
        System.out.println(res1.setName("Грибы с сыром"));

        System.out.println(res1);





    }
}