package RecipeManagerEntities;

import DatabaseAPI.RecipeDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


public class Recipe {
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean setName(String name) {
        String old_name = this.name;
        if (!Objects.isNull(getRecipeByName(name))) return false;
        this.name = name;
        if (this.updateInDB()) return true;

        this.name = old_name;
        return false;
    }

    public Category getCategory() {
        return category;
    }

    public boolean setCategory(Category category) {
        if (Objects.isNull(category)) return false;
        if (!Category.checkIsInDB(category.getId())) return false;
        this.category = category;
        this.updateInDB();
        return true;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
        this.updateInDB();
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
        this.updateInDB();
    }

    public LevelOfDifficulty getDifficultyLevel() {
        return difficulty_level;
    }

    public void setDifficultyLevel(LevelOfDifficulty difficulty_level) {
        this.difficulty_level = difficulty_level;
        this.updateInDB();
    }

    public List<IngredientWithQuantity> getIngredientsWithQuantity() {
        return ingredients_with_quantity;
    }

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

    private static Category getCategory(int category_id) {
        return Category.getCategoryById(category_id);
    }

    private static LevelOfDifficulty getLevelOfDifficulty(int level_of_difficulty_id) {
        return LevelOfDifficulty.getLevelOfDifficultyById(level_of_difficulty_id);
    }

    private static List<IngredientWithQuantity> getIngredientsWithQuantity(int recipe_id) {
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
        return recipe_db.read(id);
    }

    private static ResultSet getRecipeByNameResultSet(String name){
        RecipeDB recipe_db = new RecipeDB();
        return recipe_db.readByName(name);
    }

    private static ResultSet getRecipeByLevelOfDifficultyResultSet(LevelOfDifficulty level_of_difficulty){
        RecipeDB recipe_db = new RecipeDB();
        return recipe_db.readByLevelOfDifficulty(level_of_difficulty.getId());
    }

    private static List<RecipeData> getRecipeDataByResultSet(ResultSet rs) {
        List<RecipeData> list = new LinkedList<>();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int category = rs.getInt("category");
                String img = rs.getString("img");
                String cooking_time = rs.getString("cooking_time");
                int difficulty_level = rs.getInt("difficulty_level");
                list.add(new RecipeData(id, name, category, img, cooking_time, difficulty_level));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Recipe(RecipeData recipe_data) {
        this.id = recipe_data.id;
        this.name = recipe_data.name;
        this.category = getCategory(recipe_data.category_id);
        this.img = recipe_data.img;
        this.cookingTime = recipe_data.cookingTime;
        this.difficulty_level = getLevelOfDifficulty(recipe_data.difficulty_level_id);
        this.ingredients_with_quantity = getIngredientsWithQuantity(this.id);
    }

    public void updateIngredientsWithQuantity() {
//      обновляет список ингредиентов в рецепте (получаем актуальные данные из бд)
        this.ingredients_with_quantity = getIngredientsWithQuantity(this.id);
    }

    public static List<Recipe> getAllRecipes() {
        List<RecipeData> recipe_data_list = getRecipeDataByResultSet(getAllRecipesResultSet());
        List<Recipe> recipes_list = new LinkedList<>();

        for (RecipeData recipe_data : recipe_data_list) {
            recipes_list.add(new Recipe(recipe_data));
        }
        return recipes_list;
    }

    public static List<Recipe> getRecipesByCategory(Category category) {
        List<RecipeData> recipe_data_list = getRecipeDataByResultSet(getRecipesByCategoryResultSet(category));
        List<Recipe> recipes_list = new LinkedList<>();

        for (RecipeData recipe_data : recipe_data_list) {
            recipes_list.add(new Recipe(recipe_data));
        }
        return recipes_list;
    }

    public static List<Recipe> getRecipesByLevelOfDifficulty(LevelOfDifficulty level_of_difficulty) {
        List<RecipeData> recipe_data_list = getRecipeDataByResultSet(getRecipeByLevelOfDifficultyResultSet(level_of_difficulty));
        List<Recipe> recipes_list = new LinkedList<>();

        for (RecipeData recipe_data : recipe_data_list) {
            recipes_list.add(new Recipe(recipe_data));
        }
        return recipes_list;
    }


    public static Recipe getRecipeById(int id) {
        List<RecipeData> recipe_data_list = getRecipeDataByResultSet(getRecipeByIdResultSet(id));
        if (recipe_data_list.isEmpty()) return null;
        return new Recipe(recipe_data_list.getFirst());
    }

    public static Recipe getRecipeByName(String name) {
        List<RecipeData> recipe_data_list = getRecipeDataByResultSet(getRecipeByNameResultSet(name));
        if (recipe_data_list.isEmpty()) return null;
        return new Recipe(recipe_data_list.getFirst());
    }

    public static boolean checkIsInDB(int id) {
        Recipe r1 = getRecipeById(id);
        return !Objects.isNull(r1);
    }
    public static boolean checkIsInDB(String name) {
        Recipe r1 = getRecipeByName(name);
        return !Objects.isNull(r1);
    }

    public static Recipe addToDBAndGet(String name, Category category, String img, String cookingTime, LevelOfDifficulty difficulty_level) {
//      если рецепт с таким именем есть, то возвращает null
        if (checkIsInDB(name)) return null;

        RecipeDB recipe_db = new RecipeDB();
        boolean success = recipe_db.create(name, category.getId(), img, cookingTime, difficulty_level.getId());
        if (!success) return null;

        return getRecipeByName(name);
    }

    private boolean updateInDB() {
//      Метод возвращает false, если продукт с указанным именем уже существует
        RecipeDB recipe_db = new RecipeDB();
        return recipe_db.update(id, name, category.getId(), img, cookingTime, difficulty_level.getId());
    }

    public void delete() {
//      удаляет интредиент из бд
        RecipeDB recipe_db = new RecipeDB();
        recipe_db.delete(id);
        this.id = -1;
        name = "deleted";
        category = null;
        img = "/deleted";
        cookingTime = "-1";
        difficulty_level = null;
    }

    public boolean addNewIngredientWithQuantity(Ingredient ingredient, double quantity) {
        // ингредиента не существует
        if (Objects.isNull(ingredient)) return false;
        if (Objects.isNull(Ingredient.getIngredientById(ingredient.getId()))) return false;

        // ингредиент уже добавлен в рецепт
        if (!Objects.isNull(RecipeIngredient.getRecipeIngredientByRecipeIdAndIngredientId(this.id, ingredient.getId()))) return false;

        RecipeIngredient rs1 = RecipeIngredient.addToDBAndGet(this.id, ingredient.getId(), quantity);
        if (Objects.isNull(rs1)) return false;
        this.updateIngredientsWithQuantity();
        return true;
    }

    public void delIngredientWithQuantity(IngredientWithQuantity ingredient_with_quantity) {
        ingredient_with_quantity.delete();
        this.updateIngredientsWithQuantity();
    }

    public void delIngredientWithQuantity(int ingredient_id) {
        RecipeIngredient rs1 = RecipeIngredient.getRecipeIngredientByRecipeIdAndIngredientId(this.id, ingredient_id);
        if (Objects.isNull(rs1)) return;
        rs1.delete();
        this.updateIngredientsWithQuantity();
    }

    public double getCaloriesOfRecipe() {
        double calories = 0;
        for (IngredientWithQuantity ingredient_with_quantity : this.ingredients_with_quantity) {
            calories += ingredient_with_quantity.getCaloriesOfIngredientWithQuantity();
        }
        return calories;
    }

    public double getProteinOfRecipe() {
        double protein = 0;
        for (IngredientWithQuantity ingredient_with_quantity : this.ingredients_with_quantity) {
            protein += ingredient_with_quantity.getProteinOfIngredientWithQuantity();
        }
        return protein;
    }

    public double getFatsOfRecipe() {
        double fats = 0;
        for (IngredientWithQuantity ingredient_with_quantity : this.ingredients_with_quantity) {
            fats += ingredient_with_quantity.getFatsOfIngredientWithQuantity();
        }
        return fats;
    }

    public double getCarbsOfRecipe() {
        double carbs = 0;
        for (IngredientWithQuantity ingredient_with_quantity : this.ingredients_with_quantity) {
            carbs += ingredient_with_quantity.getCarbsOfIngredientWithQuantity();
        }
        return carbs;
    }

}

