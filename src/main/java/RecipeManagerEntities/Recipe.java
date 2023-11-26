package RecipeManagerEntities;

import java.sql.SQLException;
import java.util.List;

public class Recipe {
    private int id;

    public int getId() {
        return id;
    }

    private String name;
    private Category category;
    public String img;
    private int cookingTime;
    private LevelOfDifficulty difficultyLevel;

    private List<RecipeIngredient> recipeIngredients;


//    private static Category getCategory(int category_id) throws SQLException {
//        return Category.getCategoriesByResultSet(Category.getCategoryByIdResultSet(category_id)).getLast();
//    }
//    public static boolean checkIsInDB(String name) throws SQLException {
//        List<Ingredient> list_of_ingredients = Ingredient.getIngredientsByResultSet(Ingredient.getIngredientByNameResultSet(name));
//        if (list_of_ingredients.size() == 1) {
//            return true;
//        }
//        return false;
//
//    }
}