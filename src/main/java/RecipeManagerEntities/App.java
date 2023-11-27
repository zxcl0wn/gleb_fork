package RecipeManagerEntities;


import java.sql.SQLException;
import java.util.List;

public class App {

    public static void main(String[] args) throws SQLException {
        System.out.println("---------");

        List<IngredientWithQuantity> i1 = IngredientWithQuantity.getIngredientsWithQuantityByRecipeId(6);

        for (IngredientWithQuantity i : i1) {
            System.out.println(i.toString());
        }


    }
}