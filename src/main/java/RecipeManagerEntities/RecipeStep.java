package RecipeManagerEntities;

public class RecipeStep {
    public int getId() {
        return id;
    }

    public String getRecipeId() {
        return recipe_id;
    }

    private int id;
    private String recipe_id;
    public String text;
    public String img;

    public RecipeStep(int id, String recipe_id, String text, String img) {
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


}