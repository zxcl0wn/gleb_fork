package com.example.fefu_javafx_2;

import RecipeManagerEntities.*;
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
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
        System.out.println("поврожуду инитлизцию NIT!!!");
        ComboBoxSorting.getItems().addAll("По умолчанию", "По алфавиту: от А до Я", "По алфавиту: от Я до А", "По возрастанию Ккал", "По убыванию Ккал", "По возрастанию Б", "По убыванию Б", "По возрастанию Ж", "По убыванию Ж", "По возрастанию У", "По убыванию У", "По возрастанию сложности", "По убыванию сложности");
        ComboBoxSorting.setValue("По умолчанию");
        ComboBoxSorting.setOnAction(event -> loadRecipes());
        if (selectedIngredients == null) {
            selectedIngredients = new ArrayList<>();
        }
//        instance = this;
        if (instance == null) {
            System.out.println("нови интснус");
            instance = this;
        }


        System.out.println("diff: " + instance.selectedDifficulty);
        System.out.println("time: " + instance.selectedTime);
//        selectedDifficulty = null;
//        selectedTime = null;
//        selectedIngredients = new ArrayList<>(); // Инициализация selectedIngredients здесь
//        instance = this;

        loadCategories();
        loadRecipes();

        FavoriteCheckBox.setOnAction(this::handleFavoriteCheckBox);


    }
//    private void handleSortingChange() {
//        loadRecipes();
//    }

    public List<Recipe> filterRecipes(List<Recipe> allRecipes) {
//        List<Recipe> allRecipes = Recipe.getAllRecipes();

        List<Recipe> filteredRecipes = new ArrayList<>();

        for (Recipe recipe : allRecipes) {
            boolean difficultyMatch = (instance.selectedDifficulty == null || instance.selectedDifficulty.equals(recipe.getDifficultyLevel().getName()));
            boolean timeMatch = instance.selectedTime == null || time_comparison(recipe.getCookingTime());
            boolean ingredientsMatch = instance.selectedIngredients.isEmpty() || recipeContainsSelectedIngredients(recipe);

            System.out.println("difficultyMatch: " + difficultyMatch + ". selectedDifficulty: " + instance.selectedDifficulty);
            System.out.println("timeMatch: " + timeMatch + ". selectedTime: " + instance.selectedTime);
            System.out.println("time_comparison: " + time_comparison(recipe.getCookingTime()));
            System.out.println("ingredientsMatch: " + ingredientsMatch + ". selectedIngredients: " + instance.selectedIngredients);
            System.out.println("\n");

            if (ingredientsMatch && difficultyMatch && timeMatch) {
                filteredRecipes.add(recipe);
//                System.out.println("filteredRecipes1: " + filteredRecipes);
            }
        }
        System.out.println("filteredRecipes.size: " + filteredRecipes.size());

//        displayRecipes(filteredRecipes);
        return filteredRecipes;
//        System.out.println("filteredRecipes: " + filteredRecipes);
    }

    // Метод для загрузки рецептов
    public void loadRecipes() {
        List<Recipe> recipes;
        if (selectedCategory == null) {
            recipes = Recipe.getAllRecipes();
        } else {
            recipes = Recipe.getRecipesByCategory(selectedCategory);
        }

        if (FavoriteCheckBox.isSelected()) {
            recipes = filterFavoriteRecipes(recipes);
        }

        switch (ComboBoxSorting.getValue()) {
            case "По умолчанию":
                recipes.sort(Comparator.comparing(Recipe::getId));
                break;
            case "По алфавиту: от А до Я":
                recipes.sort(Comparator.comparing(Recipe::getName));
                break;
            case "По алфавиту: от Я до А":
                recipes.sort(Comparator.comparing(Recipe::getName).reversed());
                break;
            case "По возрастанию Ккал":
                recipes.sort(Comparator.comparingDouble(Recipe::getCaloriesOfRecipe));
                break;
            case "По убыванию Ккал":
                recipes.sort(Comparator.comparingDouble(Recipe::getCaloriesOfRecipe).reversed());
                break;
            case "По возрастанию Б":
                recipes.sort(Comparator.comparingDouble(Recipe::getProteinOfRecipe));
                break;
            case "По убыванию Б":
                recipes.sort(Comparator.comparingDouble(Recipe::getProteinOfRecipe).reversed());
                break;
            case "По возрастанию Ж":
                recipes.sort(Comparator.comparingDouble(Recipe::getFatsOfRecipe));
                break;
            case "По убыванию Ж":
                recipes.sort(Comparator.comparingDouble(Recipe::getFatsOfRecipe).reversed());
                break;
            case "По возрастанию У":
                recipes.sort(Comparator.comparingDouble(Recipe::getCarbsOfRecipe));
                break;
            case "По убыванию У":
                recipes.sort(Comparator.comparingDouble(Recipe::getCarbsOfRecipe).reversed());
                break;
            case "По возрастанию сложности":
                recipes = sortByDifficultyAscending(recipes);
                break;
            case "По убыванию сложности":
                recipes = sortByDifficultyDescending(recipes);
                break;
        }
        System.out.println("тут это до фильтрации типо: " + recipes.size());
        recipes = filterRecipes(recipes);
        System.out.println("тут это после фильтрации типо: " + recipes.size());

        System.out.println("recipes: " + recipes);
        displayRecipes(recipes);
    }

//    public void refreshRecipes() {
//        loadRecipes();
//    }

    private List<Recipe> sortByDifficultyAscending(List<Recipe> recipes) {
        List<Recipe> sortedRecipes = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            for (Recipe recipe : recipes) {
                if (recipe.getDifficultyLevel().getId() == i) {
                    sortedRecipes.add(recipe);
                }
            }
        }
        return sortedRecipes;
    }

    private List<Recipe> sortByDifficultyDescending(List<Recipe> recipes) {
        List<Recipe> sortedRecipes = new ArrayList<>();
        for (int i = 5; i >= 1; i--) {
            for (Recipe recipe : recipes) {
                if (recipe.getDifficultyLevel().getId() == i) {
                    sortedRecipes.add(recipe);
                }
            }
        }
        return sortedRecipes;
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
        System.out.println("рендерю тут да: " + recipes.size());

        for (Recipe recipe : recipes) {
            AnchorPane recipePane = createRecipeAnchorPane(recipe);
            vboxRecipes.getChildren().add(recipePane);
        }
    }

    public static void updateFilterParams(String difficulty, String time, List<String> ingredients) {
        if (instance == null) {
            return;
        }
        instance.selectedDifficulty = difficulty;
        instance.selectedTime = time;
        instance.selectedIngredients = ingredients;

        instance.loadRecipes();

        System.out.println(instance.selectedDifficulty);
        System.out.println(instance.selectedTime);
        System.out.println(instance.selectedIngredients);
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
        FilrtationController.setInstance(this);
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
//        filterRecipes();
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
    }
//    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!



    public boolean time_comparison(String recipe_time) {
        int int_recipe_time = time_convert(recipe_time);
        if (instance.selectedTime == null) {
            return false;
        }

        if (instance.selectedTime.equals("До 1 часа"))
            return int_recipe_time < 60;

        if (instance.selectedTime.equals("1-3 часа"))
            return int_recipe_time >= 60 && int_recipe_time < 180;

        if (instance.selectedTime.equals("3-5 часов"))
            return int_recipe_time >= 180 && int_recipe_time < 300;

        if (instance.selectedTime.equals("5-15 часов"))
            return int_recipe_time >= 300 && int_recipe_time < 900;

        if (instance.selectedTime.equals("15-24 часов"))
            return int_recipe_time >= 900 && int_recipe_time < 1440;

        if (instance.selectedTime.equals("24+ часов"))
            return int_recipe_time >= 1440;

        return true;
    }



    public int time_convert(String cooking_time) {
        int totalMinutes = 0;
        Pattern pattern = Pattern.compile("(\\d+) ?(д|мин|ч)");
        Matcher matcher = pattern.matcher(cooking_time);

        while (matcher.find()) {
            int value = Integer.parseInt(matcher.group(1));
            String unit = matcher.group(2);

            switch (unit) {
                case "д":
                    totalMinutes += value * 24 * 60; // день в минутах
                    break;
                case "ч":
                    totalMinutes += value * 60; // час в минутах
                    break;
                case "мин":
                    totalMinutes += value; // минута
                    break;
            }
        }
        return totalMinutes;
    }

    private boolean recipeContainsSelectedIngredients(Recipe recipe) {
        List<IngredientWithQuantity> recipeIngredients = recipe.getIngredientsWithQuantity();

        // Получаем список ингредиентов из рецепта
        List<String> recipeIngredientList = new ArrayList<>();
        for (IngredientWithQuantity ingredientWithQuantity : recipeIngredients) {
            recipeIngredientList.add(ingredientWithQuantity.getIngredient().getName());
        }

        System.out.println("recipeIngredientList: " + recipeIngredientList);

        // Проверяем, содержит ли рецепт все выбранные ингредиенты
        return recipeIngredientList.containsAll(instance.selectedIngredients);
    }


}