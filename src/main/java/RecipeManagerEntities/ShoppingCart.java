package RecipeManagerEntities;

public class ShoppingCart {
    private final int id;
    private final int recipe_ingredient_id;

    private ShoppingCart(int id, int recipe_ingredient_id) {
        this.id = id;
        this.recipe_ingredient_id = recipe_ingredient_id;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "id=" + id +
                ", recipe_ingredient_id=" + recipe_ingredient_id +
                '}';
    }

    public int getId() {
        return id;
    }

    public int getRecipeIngredientId() {
        return recipe_ingredient_id;
    }
}
