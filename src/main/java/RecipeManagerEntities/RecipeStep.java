package RecipeManagerEntities;

public class RecipeStep {
    public int getId() {
        return id;
    }

    public int getRecipeId() {
        return recipe_id;
    }

    private int id;
    private int recipe_id;
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


}