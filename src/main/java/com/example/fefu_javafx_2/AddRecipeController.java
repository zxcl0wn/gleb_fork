package com.example.fefu_javafx_2;

import RecipeManagerEntities.Category;
import RecipeManagerEntities.LevelOfDifficulty;
import RecipeManagerEntities.Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class AddRecipeController implements Initializable{
    public ComboBox<String> LevelComboBox;
    public TextField nameTextField;
    public TextField imageTextField;
    public TextField categoryTextField;
    public TextField cookingTimeTextField;
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

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        LevelComboBox.getItems().addAll("Easy", "Medium", "Hard", "Unreal", "Super Unreal");
    }


    public void onAddButtonClick(ActionEvent event) {
        String name = nameTextField.getText();
        String image = imageTextField.getText();
        String categoryName = categoryTextField.getText();
//        Category categoryName = Category.getCategoryByName(categoryTextField.getText());
        String cookingTime = cookingTimeTextField.getText();
        String difficultyLevelName = LevelComboBox.getValue();
        System.out.println("categoryName: " + categoryName);
        Category category = null;

        if (Category.getCategoryByName(categoryTextField.getText()) == null) {
            category = Category.addToDBAndGet(categoryName);
            System.out.println("Категория " + category + " была добавлена (через интерфейс)");
        } else {
            category = Category.getCategoryByName(categoryName);
        }

        LevelOfDifficulty difficultyLevel = findLevelOfDifficultyByName(difficultyLevelName);
        if (name.isEmpty() || image.isEmpty() || category == null || cookingTime.isEmpty() || difficultyLevel == null) {
            System.out.println("name: " + name);
            System.out.println("category: " + category);
            System.out.println("image: " + image);
            System.out.println("cookingTime: " + cookingTime);
            System.out.println("difficultyLevel: " + difficultyLevel);

            // не все поля заполнены
            System.out.println("Ошибка!!!");
            return;
        }
        try {
            switch_home(event);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Recipe addedRecipe = Recipe.addToDBAndGet(name, category, image, cookingTime, difficultyLevel);
        System.out.println(name + " успешно добавлен!");
    }
    private LevelOfDifficulty findLevelOfDifficultyByName(String name) {
        List<LevelOfDifficulty> levels = LevelOfDifficulty.getAllLevelsOfDifficulty();
        for (LevelOfDifficulty level : levels) {
            if (level.getName().equals(name)) {
                return level;
            }
        }
        return null;
    }

    public void AddStepButton(ActionEvent event) {

    }
    @FXML
    public void switch_add_step(Recipe recipe) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add_step.fxml"));
            Parent root = loader.load();
            AddStepController controller = loader.getController();

            controller.setRecipe(recipe);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
