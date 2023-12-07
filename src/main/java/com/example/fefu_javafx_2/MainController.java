package com.example.fefu_javafx_2;

import RecipeManagerEntities.Category;
import RecipeManagerEntities.Favorite;
import RecipeManagerEntities.Ingredient;
import RecipeManagerEntities.Recipe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    public CheckBox FavoriteCheckBox;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    VBox vboxCategories;
    @FXML
    VBox vboxRecipes;
    private Category selectedCategory;


    @FXML
    public void switch_add_category(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("add_category.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switch_home(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main_page.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switch_import_recipe(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("import_recipe.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switch_ingredients(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ingredients.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switch_cart(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("cart.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switch_add_recipe(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("add_recipe.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    ComboBox<String> ComboBoxSorting;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ComboBoxSorting.getItems().addAll("По умолчанию", "По алфавиту: от А до Я", "По алфавиту: от Я до А", "По возрастанию Ккал", "По убыванию Ккал", "По возрастанию Б", "По убыванию Б", "По возрастанию Ж", "По убыванию Ж", "По возрастанию У", "По убыванию У", "По возрастанию сложности", "По убыванию сложности");
        loadCategories();
        loadRecipes();

        FavoriteCheckBox.setOnAction(this::handleFavoriteCheckBox);

        selectedDifficulty = null;
        selectedTime = null;
        selectedIngredients = new ArrayList<>();
        instance = this;
    }
    // Метод для загрузки рецептов
    private void loadRecipes() {
        List<Recipe> recipes;

        if (selectedCategory == null) {
            recipes = Recipe.getAllRecipes();
        } else {
            recipes = Recipe.getRecipesByCategory(selectedCategory);
        }

        if (FavoriteCheckBox.isSelected()) {
            recipes = filterFavoriteRecipes(recipes);
        }

        displayRecipes(recipes);

    }
    private List<Recipe> filterFavoriteRecipes(List<Recipe> recipes) {
        List<Recipe> favoriteRecipes = new ArrayList<>();

        for (Recipe recipe : recipes) {
            Favorite favorite = Favorite.getFavoriteByRecipeId(recipe.getId());
            if (favorite != null) {
                favoriteRecipes.add(recipe);
            }
        }

        return favoriteRecipes;
    }

    private void handleFavoriteCheckBox(ActionEvent event) {
        loadRecipes(); // Перезагрузите рецепты при изменении состояния чекбокса
    }
    private String selectedDifficulty;
    private String selectedTime;
    private List<String> selectedIngredients;
    private static MainController instance;

    private void displayRecipes(List<Recipe> recipes) {
        vboxRecipes.getChildren().clear();

        for (Recipe recipe : recipes) {
            AnchorPane recipePane = createRecipeAnchorPane(recipe);
            vboxRecipes.getChildren().add(recipePane);
        }
    }

    public static void updateFilterParams(String difficulty, String time, List<String> ingredients) {
        if (instance != null) {
            instance.selectedDifficulty = difficulty;
            instance.selectedTime = time;
            instance.selectedIngredients = ingredients;
            instance.loadRecipes();

            System.out.println(instance.selectedDifficulty);
            System.out.println(instance.selectedTime);
            System.out.println(instance.selectedIngredients);
        }
    }
    private AnchorPane createRecipeAnchorPane(Recipe recipe) {
        AnchorPane recipeAnchorPane = new AnchorPane();
        recipeAnchorPane.setMinHeight(300);
        recipeAnchorPane.setMinWidth(630);

        // HBox для названия рецепта
        HBox nameHBox = new HBox();
        nameHBox.setMinHeight(50);
        nameHBox.setMinWidth(615);
        nameHBox.setAlignment(Pos.CENTER);

        Label nameLabel = new Label(recipe.getName());
        nameLabel.setFont(new Font(20.0));
        nameLabel.setAlignment(Pos.CENTER);
        nameLabel.setContentDisplay(ContentDisplay.CENTER);

        nameHBox.getChildren().add(nameLabel);
        recipeAnchorPane.getChildren().add(nameHBox);


        // HBox для калорий и времени приготовления
        HBox infoHBox = new HBox();
        infoHBox.setSpacing(200);
        infoHBox.setMinHeight(50);
        infoHBox.setMinWidth(615);
        infoHBox.setAlignment(Pos.CENTER);
        Text caloriesText = new Text(recipe.getCaloriesOfRecipe() + " калорий");


        caloriesText.setFont(new Font(15.0));
        Text timeText = new Text(recipe.getCookingTime());
        timeText.setFont(new Font(15.0));
        infoHBox.getChildren().addAll(caloriesText, timeText);

        AnchorPane.setTopAnchor(infoHBox, 50.0);
        AnchorPane.setLeftAnchor(infoHBox, 0.0);
        recipeAnchorPane.getChildren().add(infoHBox);


        // HBox для сложности и категории
        HBox difficultyHBox = new HBox();
        difficultyHBox.setSpacing(200);
        difficultyHBox.setMinHeight(50);
        difficultyHBox.setMinWidth(615);
        difficultyHBox.setAlignment(Pos.CENTER);
        Text difficultyText = new Text("Сложность: " + recipe.getDifficultyLevel().getName());
        difficultyText.setFont(new Font(15.0));
        Text categoryText = new Text("Категория: " + recipe.getCategory().getName());
        categoryText.setFont(new Font(15.0));
        difficultyHBox.getChildren().addAll(difficultyText, categoryText);

        AnchorPane.setTopAnchor(difficultyHBox, 100.0);
        AnchorPane.setLeftAnchor(difficultyHBox, 0.0);
        recipeAnchorPane.getChildren().add(difficultyHBox);


        // HBox для кнопок
        HBox buttonsHBox = new HBox();
        buttonsHBox.setSpacing(200);
        buttonsHBox.setMinHeight(50);
        buttonsHBox.setMinWidth(615);

        buttonsHBox.setAlignment(Pos.CENTER);
        Button deleteButton = new Button("Удалить");
        deleteButton.setPrefWidth(100);
        deleteButton.setPrefHeight(30);
        deleteButton.setFont(Font.font(15));
        deleteButton.setOnAction(actionEvent -> recipe.delete());

        ToggleButton favoriteButton = new ToggleButton("Любимое");
        favoriteButton.setPrefWidth(100);
        favoriteButton.setPrefHeight(30);
        favoriteButton.setFont(Font.font(15));

        Favorite favorite = Favorite.getFavoriteByRecipeId(recipe.getId());
        if (favorite != null) {
            favoriteButton.setSelected(true);
        }

        favoriteButton.setOnAction(actionEvent -> {
            if (favoriteButton.isSelected()) {
                Favorite.addToDBAndGet(recipe.getId());
            } else {
                Favorite favoriteToRemove = Favorite.getFavoriteByRecipeId(recipe.getId());
                if (favoriteToRemove != null) {
                    favoriteToRemove.delete();
                }
            }
        });


        buttonsHBox.getChildren().addAll(deleteButton, favoriteButton);
        AnchorPane.setTopAnchor(buttonsHBox, 150.0);
        AnchorPane.setLeftAnchor(buttonsHBox, 0.0);
        recipeAnchorPane.getChildren().add(buttonsHBox);


        // HBox для кнопки "Посмотреть рецепт"
        HBox viewButtonHBox = new HBox();
        viewButtonHBox.setSpacing(200);
        viewButtonHBox.setMinHeight(50);
        viewButtonHBox.setMinWidth(615);
        viewButtonHBox.setAlignment(Pos.CENTER);
        Button viewButton = new Button("Посмотреть");
        viewButton.setOnAction(actionEvent -> {
            try {
                switch_view(recipe);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        viewButton.setPrefWidth(150);
        viewButton.setPrefHeight(40);
        viewButton.setFont(Font.font(20));
        viewButtonHBox.getChildren().add(viewButton);
        AnchorPane.setTopAnchor(viewButtonHBox, 200.0);
        AnchorPane.setLeftAnchor(viewButtonHBox, 0.0);
        recipeAnchorPane.getChildren().add(viewButtonHBox);

        return recipeAnchorPane;
    }

    private void loadCategories() {
        List<Category> categories = Category.getAllCategoriesList();

        for (Category category: categories) {
            VBox categoryBox = createCategoryBox(category);

            vboxCategories.getChildren().add(categoryBox);
        }
    }

    private VBox createCategoryBox(Category category) {
//       Кнопка с названием категории
        Button categoryButton = new Button(category.getName());
        categoryButton.setOnAction(event -> onCategoryButtonClick(category));
        categoryButton.setAlignment(Pos.CENTER);
        categoryButton.setMinHeight(60);
        categoryButton.setMaxHeight(60);
        categoryButton.setMinWidth(140);
        categoryButton.setMaxWidth(140);


//        Кнопка удаление категории
        Button deleteButton = new Button("Удалить");
        deleteButton.setOnAction(event -> onDeleteButtonClick(category));
        deleteButton.setAlignment(Pos.CENTER);
        deleteButton.setMinHeight(30);
        deleteButton.setMaxHeight(30);
        deleteButton.setMinWidth(140);
        deleteButton.setMaxWidth(140);


        VBox buttonAndDeleteVbox = new VBox(categoryButton, deleteButton);
        buttonAndDeleteVbox.setSpacing(2);
        vboxCategories.getChildren().add(buttonAndDeleteVbox);

        VBox categoryBox = new VBox(buttonAndDeleteVbox);
        vboxCategories.setSpacing(20);

        return buttonAndDeleteVbox;
    }


    private void onDeleteButtonClick(Category category) {
        if (category != null) {
            category.delete();
        }
    }

    private void onCategoryButtonClick(Category category) {
        System.out.println("Category clicked: " + category.getName());
        selectedCategory = category; // Set the selected category
        loadRecipes();
    }

    @FXML
    public void switch_filtration(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("filtration.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    public void switch_view(Recipe recipe) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("view_page.fxml"));
            Parent root = loader.load();
            ViewPageController controller = loader.getController();

            controller.setRecipe(recipe);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void all_recipes(ActionEvent event) {
        selectedCategory = null;
        loadRecipes();
//        qwewqe
    }
}