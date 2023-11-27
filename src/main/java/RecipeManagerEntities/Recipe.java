package RecipeManagerEntities;

import DatabaseAPI.RecipeDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Recipe {
    private class RecipeData {
        public int id;
        public String name;
        public int category_id;
        public String img;
        public String cookingTime;
        public int difficulty_level_id;

        public RecipeData(int id, String name, int category_id, String img, String cookingTime, int difficulty_level_id) {
            this.id = id;
            this.name = name;
            this.category_id = category_id;
            this.img = img;
            this.cookingTime = cookingTime;
            this.difficulty_level_id = difficulty_level_id;
        }

        @Override
        public String toString() {
            return "RecipeData{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", category_id=" + category_id +
                    ", img='" + img + '\'' +
                    ", cookingTime='" + cookingTime + '\'' +
                    ", difficulty_level_id=" + difficulty_level_id +
                    '}';
        }
    }
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


    private static ResultSet getAllRecipesResultSet(){
        RecipeDB recipe_db = new RecipeDB();
        return recipe_db.readAll();
    }

    private static ResultSet getIngredientByNameResultSet(String name){
        RecipeDB recipe_db = new RecipeDB();
        return recipe_db.readByName(name);
    }

    private static ResultSet getRecipeByIdResultSet(int id){
        RecipeDB recipe_db = new RecipeDB();
        return recipe_db.readAll();
    }

    private static ResultSet getRecipeByCategoryResultSet(Category category){
        RecipeDB recipe_db = new RecipeDB();
        return recipe_db.readByCategory(category.getId());
    }

    private static ResultSet getRecipeByLevelOfDifficultyResultSet(LevelOfDifficulty level_of_difficulty){
        RecipeDB recipe_db = new RecipeDB();
        return recipe_db.readByCategory(level_of_difficulty.getId());
    }








}