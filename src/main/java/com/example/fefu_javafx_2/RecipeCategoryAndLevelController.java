package com.example.fefu_javafx_2;

import Parser100menu.Ingredient100Menu;
import Parser100menu.Recipe100Menu;
import Parser100menu.RecipeParser;
import Parser100menu.RecipeStep100Menu;
import RecipeManagerEntities.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.ResourceBundle;

public class RecipeCategoryAndLevelController implements Initializable{
    public ComboBox categoryComboBox;
    public ComboBox levelComboBox;
    private Stage stage;
    private Scene scene;
    private Parent root;
    public Recipe100Menu recipe;

    private String reverse_time_convert(int time) {
        int days = time / (24 * 60);
        int hours = (time % (24 * 60)) / 60;
        int minutes = time % 60;

        StringBuilder result = new StringBuilder();
        if (days > 0) {
            result.append(days).append(" д ");
        }
        if (hours > 0) {
            result.append(hours).append(" ч ");
        }
        if (minutes > 0) {
            result.append(minutes).append(" мин");
        }
        return result.toString().trim();
    }


    @FXML
    public void switch_home(javafx.event.ActionEvent actionEvent) throws IOException {
        int level_id = -1;

        if (levelComboBox.getValue() == "Easy") {
            level_id = 1;
        } else if (levelComboBox.getValue() == "Medium") {
            level_id = 2;
        } else if (levelComboBox.getValue() == "Hard") {
            level_id = 3;
        } else if (levelComboBox.getValue() == "Unreal") {
            level_id = 4;
        } else if (levelComboBox.getValue() == "Super Unreal") {
            level_id = 5;
        }

        String image_link = "src/main/resources/com/example/fefu_javafx_2/images/" + recipe.name + "/main_image/" + recipe.name + ".jpg";
        System.out.println("TIME!!!    " + reverse_time_convert(recipe.cooking_time));
//        Добавление рецепта
        if (Category.getCategoryByName(recipe.category) == null) {
            Recipe.addToDBAndGet(recipe.name, Category.addToDBAndGet(recipe.category), image_link, reverse_time_convert(recipe.cooking_time), LevelOfDifficulty.getLevelOfDifficultyById(level_id));
        } else {
            Recipe.addToDBAndGet(recipe.name, Category.getCategoryByName(recipe.category), image_link, reverse_time_convert(recipe.cooking_time), LevelOfDifficulty.getLevelOfDifficultyById(level_id));
        }

//        Добавление ингредиентов
        for (int i=0; i<recipe.ingredients.length; i++) {
            Ingredient100Menu one_ingredient = recipe.getIngredient(i);
            Recipe.getRecipeByName(recipe.name).addNewIngredientWithQuantity(Ingredient.getIngredientByName(one_ingredient.name), one_ingredient.quantity);
        }


//        Добавление шагов
        System.out.println();
        for (int i=0; i<recipe.recipe_steps.length; i++) {
            RecipeStep100Menu one_step = recipe.getRecipeStep(i);
            String absolute_path = "C:\\Users\\m_mar\\IdeaProjects\\gleb_fork\\src\\main\\resources\\com\\example\\fefu_javafx_2\\images\\" + recipe.name + "\\steps\\" + recipe.name + " шаг " + (i+1) + ".jpg";
            RecipeStep.addToDBAndGet(Recipe.getRecipeByName(recipe.name).getId(), one_step.text, absolute_path);
            System.out.println("ABSOLUT!!!   " + absolute_path);

        }

        Parent root = FXMLLoader.load(getClass().getResource("main_page.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryComboBox.getItems().addAll("Easy", "Medium", "Hard", "Unreal", "Super Unreal");
        levelComboBox.getItems().addAll("Easy", "Medium", "Hard", "Unreal", "Super Unreal");
    }


    public void setRecipe(Recipe100Menu recipe) {
        this.recipe = recipe;
    }
}
