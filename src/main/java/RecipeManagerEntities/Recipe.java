package RecipeManagerEntities;

import DatabaseAPI.IngredientDB;
import DatabaseAPI.RecipeDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


public class Recipe {
    private static class RecipeData {
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
    private LevelOfDifficulty difficulty_level;
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
                ", difficultyLevel=" + difficulty_level +
                ", ingredients_with_quantity=" + ingredients_with_quantity +
                '}';
    }

    private static ResultSet getAllRecipesResultSet(){
        RecipeDB recipe_db = new RecipeDB();
        return recipe_db.readAll();
    }

    private static ResultSet getRecipesByCategoryResultSet(Category category){
        RecipeDB recipe_db = new RecipeDB();
        return recipe_db.readByCategory(category.getId());
    }

    private static ResultSet getRecipeByIdResultSet(int id){
        RecipeDB recipe_db = new RecipeDB();
        return recipe_db.readAll();
    }

    private static ResultSet getRecipeByNameResultSet(String name){
        RecipeDB recipe_db = new RecipeDB();
        return recipe_db.readByName(name);
    }

    private static ResultSet getRecipeByLevelOfDifficultyResultSet(LevelOfDifficulty level_of_difficulty){
        RecipeDB recipe_db = new RecipeDB();
        return recipe_db.readByLevelOfDifficulty(level_of_difficulty.getId());
    }

    private static List<RecipeData> getRecipeDataByResultSet(ResultSet rs) throws SQLException {
        List<RecipeData> list = new LinkedList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int category = rs.getInt("category");
            String img = rs.getString("img");
            String cooking_time = rs.getString("cooking_time");
            int difficulty_level = rs.getInt("difficulty_level");

            list.add(new RecipeData(id, name, category, img, cooking_time, difficulty_level));
        }
        return list;
    }

    private Recipe(RecipeData recipe_data) throws SQLException {
        this.id = recipe_data.id;
        this.name = recipe_data.name;
        this.category = getCategory(recipe_data.category_id);
        this.img = recipe_data.img;
        this.cookingTime = recipe_data.cookingTime;
        this.difficulty_level = getLevelOfDifficulty(recipe_data.difficulty_level_id);
        this.ingredients_with_quantity = getIngredientsWithQuantity(this.id);
    }

    public void updateIngredientsWithQuantity() throws SQLException {
//      обновляет список ингредиентов в рецепте (получаем актуальные данные из бд)
        this.ingredients_with_quantity = getIngredientsWithQuantity(this.id);
    }

    public static List<Recipe> getAllRecipes() throws SQLException {
        List<RecipeData> recipe_data_list = getRecipeDataByResultSet(getAllRecipesResultSet());
        List<Recipe> recipes_list = new LinkedList<>();

        for (RecipeData recipe_data : recipe_data_list) {
            recipes_list.add(new Recipe(recipe_data));
        }
        return recipes_list;
    }

    public static List<Recipe> getRecipesByCategory(Category category) throws SQLException {
        List<RecipeData> recipe_data_list = getRecipeDataByResultSet(getRecipesByCategoryResultSet(category));
        List<Recipe> recipes_list = new LinkedList<>();

        for (RecipeData recipe_data : recipe_data_list) {
            recipes_list.add(new Recipe(recipe_data));
        }
        return recipes_list;
    }

    public static List<Recipe> getRecipesByLevelOfDifficulty(LevelOfDifficulty level_of_difficulty) throws SQLException {
        List<RecipeData> recipe_data_list = getRecipeDataByResultSet(getRecipeByLevelOfDifficultyResultSet(level_of_difficulty));
        List<Recipe> recipes_list = new LinkedList<>();

        for (RecipeData recipe_data : recipe_data_list) {
            recipes_list.add(new Recipe(recipe_data));
        }
        return recipes_list;
    }


    public static Recipe getRecipeById(int id) throws SQLException {
        List<RecipeData> recipe_data_list = getRecipeDataByResultSet(getRecipeByIdResultSet(id));
        if (recipe_data_list.isEmpty()) return null;
        return new Recipe(recipe_data_list.getFirst());
    }

    public static Recipe getRecipeByName(String name) throws SQLException {
        List<RecipeData> recipe_data_list = getRecipeDataByResultSet(getRecipeByNameResultSet(name));
        if (recipe_data_list.isEmpty()) return null;
        return new Recipe(recipe_data_list.getFirst());
    }

    public static boolean checkIsInDB(int id) throws SQLException {
        Recipe r1 = getRecipeById(id);
        return !Objects.isNull(r1);
    }
    public static boolean checkIsInDB(String name) throws SQLException {
        Recipe r1 = getRecipeByName(name);
        return !Objects.isNull(r1);
    }

    public static Recipe addToDBAndGet(String name, Category category, String img, String cookingTime, LevelOfDifficulty difficulty_level) throws SQLException {
//      если рецепт с таким именем есть, то возвращает null
        if (checkIsInDB(name)) return null;

        RecipeDB recipe_db = new RecipeDB();
        boolean success = recipe_db.create(name, category.getId(), img, cookingTime, difficulty_level.getId());
        if (!success) return null;

        return getRecipeByName(name);
    }






}