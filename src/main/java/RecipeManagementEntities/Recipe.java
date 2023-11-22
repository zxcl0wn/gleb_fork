package RecipeManagementEntities;

public class Recipe {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String name;
    private Category category;
    private String img;
    private int cookingTime;
    private LevelOfDifficulty difficultyLevel;

}