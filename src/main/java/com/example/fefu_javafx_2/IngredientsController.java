package com.example.fefu_javafx_2;

import RecipeManagerEntities.Ingredient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
public class IngredientsController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    VBox ingredientsVBox;


    @FXML
    public void switch_home(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main_page.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switch_add_ingredient(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("add_ingredient.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void switch_change_ingredient(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("change_ingredient.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadIngredients();
    }


    private void loadIngredients() {
        List<Ingredient> ingredients = Ingredient.getAllIngredientsList();

        for (Ingredient ingredient : ingredients) {
            HBox ingredientHBox = createIngredientHBox(ingredient);
            ingredientsVBox.getChildren().add(ingredientHBox);
        }
    }
    private HBox createIngredientHBox(Ingredient ingredient) {
        HBox hbox = new HBox();
        Insets textInsets = new Insets(8, 5, 0, 0);
        hbox.setPadding(new Insets(10, 0, 0, 0));
        hbox.setPrefSize(762, 53);


        Text nameText = new Text(ingredient.getName());
        nameText.setWrappingWidth(183.73666381835938);
        nameText.setFont(new Font(18));
        HBox.setMargin(nameText, textInsets);
        nameText.setTextAlignment(TextAlignment.CENTER);

        Text caloriesText = new Text(ingredient.getCalories() + " калорий");
        caloriesText.setWrappingWidth(144.53671264648438);
        caloriesText.setFont(new Font(18));
        HBox.setMargin(caloriesText, textInsets);
        caloriesText.setTextAlignment(TextAlignment.CENTER);

        Text proteinText = new Text(ingredient.getProtein() + " Б");
        proteinText.setWrappingWidth(64.53670501708984);
        proteinText.setFont(new Font(18));
        HBox.setMargin(proteinText, textInsets);
        proteinText.setTextAlignment(TextAlignment.CENTER);

        Text fatsText = new Text(ingredient.getFats() + " Ж");
        fatsText.setWrappingWidth(64.53670501708984);
        fatsText.setFont(new Font(18));
        HBox.setMargin(fatsText, textInsets);
        fatsText.setTextAlignment(TextAlignment.CENTER);

        Text carbsText = new Text(ingredient.getCarbs() + " У");
        carbsText.setWrappingWidth(64.53670501708984);
        carbsText.setFont(new Font(18));
        HBox.setMargin(carbsText, textInsets);
        carbsText.setTextAlignment(TextAlignment.CENTER);


        Button deleteButton = new Button("Удалить");
        deleteButton.setOnAction(event -> onDeleteButtonClick(ingredient));
        deleteButton.setAlignment(Pos.CENTER);
        deleteButton.setPrefWidth(102);
        deleteButton.setPrefHeight(34);
        HBox.setMargin(deleteButton, textInsets);
        deleteButton.setTextAlignment(TextAlignment.CENTER);


        Button editButton = new Button("Изменить");
        editButton.setOnAction(event -> onEditButtonClick(ingredient));
        editButton.setAlignment(Pos.CENTER);
        editButton.setPrefWidth(102);
        editButton.setPrefHeight(34);
        HBox.setMargin(editButton, textInsets);
        editButton.setTextAlignment(TextAlignment.CENTER);
        editButton.setOnAction(actionEvent -> {
            try {
                switch_change_ingredient(actionEvent);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


        hbox.getChildren().addAll(nameText, caloriesText, proteinText, fatsText, carbsText, deleteButton, editButton);
        return hbox;
    }


    private void onDeleteButtonClick(Ingredient ingredient) {
        // Удаляем ингредиент из базы данных
        ingredient.delete();

        // Удаляем соответствующий HBox из VBox на интерфейсе
        ingredientsVBox.getChildren().removeIf(node -> node instanceof HBox && ((HBox) node).getChildren().contains(ingredient));

        System.out.println("Ингредиент удален: " + ingredient.getName());
    }

    private void onEditButtonClick(Ingredient ingredient) {
        // Реализуйте логику для редактирования ингредиента
    }
}
