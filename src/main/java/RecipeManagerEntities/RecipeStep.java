package RecipeManagerEntities;

import DatabaseAPI.RecipeStepsDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RecipeStep {
    public int getId() {
        return id;
    }

    public int getRecipeId() {
        return recipe_id;
    }

    private final int id;
    private final int recipe_id;
    public String text;
    public String img;

    public RecipeStep(int id, int recipe_id, String text, String img) {
        this.id = id;
        this.recipe_id = recipe_id;
        this.text = text;
        this.img = img;
    }

    @Override
    public String toString() {
        return "RecipeStep{" +
                "id=" + id +
                ", recipe=" + recipe_id +
                ", text='" + text + '\'' +
                ", img='" + img + '\'' +
                '}';
    }

    public static ResultSet getAllRecipeStepsResultSet(){
        RecipeStepsDB recipe_steps_db = new RecipeStepsDB();
        return recipe_steps_db.readAll();
    }

    public static ResultSet getRecipeStepByRecipeIdResultSet(int recipe_id){
        RecipeStepsDB ingredients_db = new RecipeStepsDB();
        return ingredients_db.readByRecipeId(recipe_id);
    }

    public static List<RecipeStep> getRecipeStepByResultSet(ResultSet rs) throws SQLException {
        List<RecipeStep> list = new LinkedList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            int recipe_id = rs.getInt("recipe_id");
            String text = rs.getString("text");
            String protein = rs.getString("img");

            list.add(new RecipeStep(id, recipe_id, text, protein));
        }
        return list;
    }

    public static RecipeStep addToDBAndGet(int recipe_id, String text, String img) throws SQLException {

        RecipeStepsDB recipe_steps_db = new RecipeStepsDB();
        recipe_steps_db.create(recipe_id, text, img);

        List<RecipeStep> list_of_recipe_steps = RecipeStep.getRecipeStepByResultSet(RecipeStep.getAllRecipeStepsResultSet());
        return list_of_recipe_steps.getLast();
    }

    public void updateInDB() {
        RecipeStepsDB recipe_steps_db = new RecipeStepsDB();
        recipe_steps_db.update(id, recipe_id, text, img);
    }


}