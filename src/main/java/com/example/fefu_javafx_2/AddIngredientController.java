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


public class AddIngredientController {
    private Stage stage;
    private Scene scene;
    private Parent root;

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

    @FXML
    public void switch_ingredients(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ingredients.fxml"));
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
    public void add_new_ingredient(javafx.event.ActionEvent actionEvent) throws IOException {
        String name = nameTextField.getText();
        String calories = caloriesTextField.getText();
        String protein = proteinTextField.getText();
        String fats = fatsTextField.getText();
        String carbs = carbsTextField.getText();

        if (name.isEmpty() || calories.isEmpty() || protein.isEmpty() || fats.isEmpty() || carbs.isEmpty()) {
            System.out.println("Пожалуйста, заполните все поля");
            return;
        }

        Ingredient new_ingredient = Ingredient.addToDBAndGet(name, Double.parseDouble(calories), Double.parseDouble(protein), Double.parseDouble(fats), Double.parseDouble(carbs));
        if (new_ingredient == null) {
            System.out.println("Такой ингредиент уже есть");
            return;
        }

        System.out.println("Ингредиент " + new_ingredient.getName() +  " был добавлен");
    }
}
