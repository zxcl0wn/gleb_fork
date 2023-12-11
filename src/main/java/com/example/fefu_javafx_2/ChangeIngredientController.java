package com.example.fefu_javafx_2;

import RecipeManagerEntities.Ingredient;
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

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChangeIngredientController implements Initializable {
    private Stage stage;
    private Scene scene;
    @FXML
    private TextField nameTextField;

    @FXML
    private TextField caloriesTextField;

    @FXML
    private TextField proteinTextField;

    @FXML
    private TextField fatsTextField;

    @FXML
    private TextField carbsTextField;

    private Ingredient ingredient;
    private IngredientsController parentController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        parentController = IngredientsController.getInstance();
        parentController.setChangeIngredientController(this);
    }
    public void setParentController(IngredientsController parentController) {
        this.parentController = parentController;
    }

public void setIngredient(Ingredient ingredient) {
    this.ingredient = ingredient;

    nameTextField.setText(ingredient.getName());
    caloriesTextField.setText(String.valueOf(ingredient.getCalories()));
    proteinTextField.setText(String.valueOf(ingredient.getProtein()));
    fatsTextField.setText(String.valueOf(ingredient.getFats()));
    carbsTextField.setText(String.valueOf(ingredient.getCarbs()));
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
    public void change_name(javafx.event.ActionEvent actionEvent) throws IOException {
        try {
            String newName = nameTextField.getText();

            if (ingredient.setName(newName)) {
                System.out.println("Название изменено на " + newName);
                parentController.refreshIngredients();
            } else {
                System.out.println("Ингредиент с таким именем уже существует");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка при вводе значения");
        }
    }

    @FXML
    public void change_calories(javafx.event.ActionEvent actionEvent) throws IOException {
        try {
            double newCalories = Double.parseDouble(caloriesTextField.getText());

            if (ingredient.setCalories(newCalories)) {
                parentController.refreshIngredients();
                System.out.println("Количество калорий изменено на " + newCalories);
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка при вводе значения");
        }
    }

    @FXML
    public void change_protein(javafx.event.ActionEvent actionEvent) throws IOException {
        try {
            double newProteins = Double.parseDouble(proteinTextField.getText());

            if (ingredient.setProtein(newProteins)) {
                parentController.refreshIngredients();
                System.out.println("Количество белков изменено на " + newProteins);
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка при вводе значения");
        }
    }

    @FXML
    public void change_fats(javafx.event.ActionEvent actionEvent) throws IOException {
        try {
            double newFats = Double.parseDouble(fatsTextField.getText());

            if (ingredient.setFats(newFats)) {
                parentController.refreshIngredients();
                System.out.println("Количество жиров изменено на " + newFats);
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка при вводе значения");
        }
    }

    @FXML
    public void change_carbs(javafx.event.ActionEvent actionEvent) throws IOException {
        try {
            double newCarbs = Double.parseDouble(carbsTextField.getText());

            if (ingredient.setCarbs(newCarbs)) {
                parentController.refreshIngredients();
                System.out.println("Количество углеводов изменено на " + newCarbs);
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка при вводе значения");
        }
    }
}
