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

    private final int id;
    public String name;
    public double calories;
    public double protein;
    public double fats;
    public double carbs;

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

    public static Ingredient addToDBAndGet(String name, double calories, double protein, double fats, double carbs) throws SQLException {

        List<Ingredient> list_of_ingredients = Ingredient.getIngredientsByResultSet(Ingredient.getIngredientByNameResultSet(name));

        if (list_of_ingredients.size() == 1) {
            return list_of_ingredients.get(0);
        }

        IngredientDB ingredients_db = new IngredientDB();
        ingredients_db.create(name, calories, protein, fats, carbs);

        list_of_ingredients = Ingredient.getIngredientsByResultSet(Ingredient.getIngredientByNameResultSet(name));
        return list_of_ingredients.get(0);
    }

    public boolean updateInDB() {
//      Метод возвращает false, если продукт с указанным именем уже существует
        IngredientDB ingredients_db = new IngredientDB();
        return ingredients_db.update(id, name, calories, protein, fats, carbs);
    }

}


