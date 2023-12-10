package com.example.fefu_javafx_2;

import Parser100menu.Recipe100Menu;
import RecipeManagerEntities.LevelOfDifficulty;
import RecipeManagerEntities.Recipe;
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
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import Parser100menu.RecipeParser;
import java.io.IOException;
import java.util.Arrays;

public class ImportRecipeController {
    public TextField recipeTextField;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void switch_home(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main_page.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switch_recipe_category_and_level(javafx.event.ActionEvent actionEvent) throws IOException {
        Recipe100Menu recipe = new RecipeParser(recipeTextField.getText()).getRecipeByUrl();
//        System.out.println("!!!all_ingredients: " + Arrays.toString(recipe.ingredients));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("recipe_category_and_level.fxml"));
        Parent root = loader.load();
        RecipeCategoryAndLevelController controller = loader.getController();
        controller.setRecipe(recipe);

        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
