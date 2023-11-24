package RecipeManagerEntities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class Ingredient {
    public int id;
    public String name;
    public double calories;
    public double protein;
    public double fats;
    public double carbs;

    public Ingredient(int id, String name, double calories, double protein, double fats, double carbs) {
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

    public static List<Ingredient> getIngredientsByResultSet(ResultSet rs) throws SQLException {
        List<Ingredient> list = new ArrayList<>();
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
}


class IngredientEntry {
    private Ingredient ingredient;

    public IngredientEntry (String name, double calories, double protein, double fats, double carbs) {

    }

    // getters and setters
}