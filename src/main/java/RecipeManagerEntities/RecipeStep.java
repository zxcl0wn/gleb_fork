package RecipeManagerEntities;

import DatabaseAPI.RecipeStepsDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class RecipeStep {
    private int id;
    private int recipe_id;
    private String text;
    private String img;

    private RecipeStep(int id, int recipe_id, String text, String img) {
        this.id = id;
        this.recipe_id = recipe_id;
        this.text = text;
        this.img = img;
    }

    private static ResultSet getAllRecipeStepsResultSet(){
        RecipeStepsDB recipe_steps_db = new RecipeStepsDB();
        return recipe_steps_db.readAll();
    }

    private static ResultSet getRecipeStepByRecipeIdResultSet(int recipe_id){
        RecipeStepsDB ingredients_db = new RecipeStepsDB();
        return ingredients_db.readByRecipeId(recipe_id);
    }

    private static ResultSet getRecipeStepByIdResultSet(int id){
        RecipeStepsDB ingredients_db = new RecipeStepsDB();
        return ingredients_db.read(id);
    }

    private static List<RecipeStep> getRecipeStepsByResultSet(ResultSet rs) throws SQLException {
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

    public static List<RecipeStep> getAllRecipeSteps() throws SQLException {
        return getRecipeStepsByResultSet(getAllRecipeStepsResultSet());
    }

    public static List<RecipeStep> getRecipeStepsByRecipeId(int recipe_id) throws SQLException {
        return getRecipeStepsByResultSet(getRecipeStepByRecipeIdResultSet(recipe_id));
    }

    public static RecipeStep getRecipeStepById(int id) throws SQLException {
        List<RecipeStep> rs1 = getRecipeStepsByResultSet(getRecipeStepByIdResultSet(id));
        if (rs1.isEmpty()) return null;
        return rs1.getFirst();
    }

    public static RecipeStep addToDBAndGet(int recipe_id, String text, String img) throws SQLException {
        // null - если не существует рецепта с rexipe_id
        RecipeStepsDB recipe_steps_db = new RecipeStepsDB();
        boolean successful_operation = recipe_steps_db.create(recipe_id, text, img);
        if (!successful_operation) return null;

        List<RecipeStep> list_of_recipe_steps = getRecipeStepsByRecipeId(recipe_id);
        return list_of_recipe_steps.getLast();
    }

    private void updateInDB() {
        RecipeStepsDB recipe_steps_db = new RecipeStepsDB();
        recipe_steps_db.update(id, recipe_id, text, img);
    }

    public void delete() {
//      удаляет шаг рецепта из бд
        RecipeStepsDB recipe_steps_db = new RecipeStepsDB();
        recipe_steps_db.delete(id);
        id = -1;
        recipe_id = -1;
        text = "deleted";
        img = "/deleted";
    }


    public int getId() {
        return id;
    }

    public int getRecipeId() {
        return recipe_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        this.updateInDB();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
        this.updateInDB();
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
}