package RecipeManagerEntities;

import java.sql.SQLException;
import java.util.List;

public class Recipe {
    private int id;
    private String name;
    private Category category;
    private String img;
    private String cookingTime;
    private LevelOfDifficulty difficultyLevel;
    private List<IngredientWithQuantity> ingredients_with_quantity;


    private static Category getCategory(int category_id) throws SQLException {
        return Category.getCategoryById(category_id);
    }

    private static LevelOfDifficulty getLevelOfDifficulty(int level_of_difficulty_id) throws SQLException {
        return LevelOfDifficulty.getLevelOfDifficultyById(level_of_difficulty_id);
    }

    private static List<IngredientWithQuantity> getIngredientsWithQuantity(int recipe_id) throws SQLException {
        return IngredientWithQuantity.getIngredientsWithQuantityByRecipeId(recipe_id);
    }
    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", img='" + img + '\'' +
                ", cookingTime='" + cookingTime + '\'' +
                ", difficultyLevel=" + difficultyLevel +
                ", ingredients_with_quantity=" + ingredients_with_quantity +
                '}';
    }



}