package RecipeManagerEntities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import DatabaseAPI.IngredientDB;

class Ingredient {
    public int getId() {
        return id;
    }

    private int id;
    private String name;
    private double calories;
    private double protein;
    private double fats;
    private double carbs;

    private Ingredient(int id, String name, double calories, double protein, double fats, double carbs) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.fats = fats;
        this.carbs = carbs;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", calories=" + calories +
                ", protein=" + protein +
                ", fats=" + fats +
                ", carbs=" + carbs +
                '}';
    }

    public static ResultSet getAllIngredientsResultSet(){
        IngredientDB ingredients_db = new IngredientDB();
        return ingredients_db.readAll();
    }

    public static ResultSet getIngredientByNameResultSet(String name){
        IngredientDB ingredients_db = new IngredientDB();
        return ingredients_db.readByName(name);
    }

    public static List<Ingredient> getIngredientsByResultSet(ResultSet rs) throws SQLException {
        List<Ingredient> list = new LinkedList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            double calories = rs.getDouble("calories");
            double protein = rs.getDouble("protein");
            double fats = rs.getDouble("fats");
            double carbs = rs.getDouble("carbs");

            list.add(new Ingredient(id, name, calories, protein, fats, carbs));
        }
        return list;
    }

    public static boolean checkIsInDB(String name) throws SQLException {
        List<Ingredient> list_of_ingredients = Ingredient.getIngredientsByResultSet(Ingredient.getIngredientByNameResultSet(name));
        if (list_of_ingredients.size() == 1) {
            return true;
        }
        return false;
    }

    public static Ingredient addToDBAndGet(String name, double calories, double protein, double fats, double carbs) throws SQLException {
//      если ингредиент с таким именем есть, то возвращает null
        List<Ingredient> list_of_ingredients = Ingredient.getIngredientsByResultSet(Ingredient.getIngredientByNameResultSet(name));

        if (list_of_ingredients.size() == 1) {
            return null;
        }

        IngredientDB ingredients_db = new IngredientDB();
        ingredients_db.create(name, calories, protein, fats, carbs);

        list_of_ingredients = Ingredient.getIngredientsByResultSet(Ingredient.getIngredientByNameResultSet(name));
        return list_of_ingredients.get(0);
    }

    private boolean updateInDB() {
//      Метод возвращает false, если продукт с указанным именем уже существует
        IngredientDB ingredients_db = new IngredientDB();
        return ingredients_db.update(id, name, calories, protein, fats, carbs);
    }

    public void delete() {
//      удаляет интредиент из бд
        IngredientDB ingredients_db = new IngredientDB();
        ingredients_db.delete(id);
        this.id = -1;
    }

    public String getName() {
        return name;
    }

    public boolean setName(String name) throws SQLException {
        if (Ingredient.checkIsInDB(name)) return false;
        String old_name = this.name;
        this.name = name;
        if (this.updateInDB()) return true;
        this.name = old_name;
        return false;
    }

    public double getCalories() {
        return calories;
    }

    public boolean setCalories(double calories) {
        this.calories = calories;
        return this.updateInDB();
    }

    public double getProtein() {
        return protein;
    }

    public boolean setProtein(double protein) {
        this.protein = protein;
        return this.updateInDB();
    }

    public double getFats() {
        return fats;
    }

    public boolean setFats(double fats) {
        this.fats = fats;
        return this.updateInDB();
    }

    public double getCarbs() {
        return carbs;
    }

    public boolean setCarbs(double carbs) {
        this.carbs = carbs;
        return this.updateInDB();
    }
}


