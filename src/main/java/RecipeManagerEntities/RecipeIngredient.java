package RecipeManagerEntities;

public class RecipeIngredient {
    private int id;
    private int recipe_id;
    private int ingredient_id;
    private double quantityOfIngredient;

    private RecipeIngredient(int id, int recipe_id, int ingredient_id, double quantityOfIngredient) {
        this.id = id;
        this.recipe_id = recipe_id;
        this.ingredient_id = ingredient_id;
        this.quantityOfIngredient = quantityOfIngredient;
    }





    @Override
    public String toString() {
        return "RecipeIngredient{" +
                "id=" + id +
                ", recipe_id=" + recipe_id +
                ", ingredient_id=" + ingredient_id +
                ", quantityOfIngredient=" + quantityOfIngredient +
                '}';
    }

    public int getId() {
        return id;
    }

    public int getRecipeId() {
        return recipe_id;
    }

    public int getIngredientId() {
        return ingredient_id;
    }

    public double getQuantityOfIngredient() {
        return quantityOfIngredient;
    }

    public void setQuantityOfIngredient(double quantityOfIngredient) {
        this.quantityOfIngredient = quantityOfIngredient;
    }
}


