package com.example.fefu_javafx_2;

import RecipeManagerEntities.Ingredient;
import RecipeManagerEntities.Recipe;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class addIngredientInRecipeController implements Initializable{
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private VBox ingredientsVBox;
    private Recipe selectedRecipe;


    public void closeButton(javafx.event.ActionEvent event) {
//        Stage stageToClose = (Stage) ((Node) event.getSource()).getScene().getWindow();
//
//        // Закрываем окно
//        stageToClose.close();
    }

    public void setSelectedRecipe(Recipe selectedRecipe) {
        this.selectedRecipe = selectedRecipe;
        System.out.println("!!!" + selectedRecipe);
    }

    public void addIngredientButton(javafx.event.ActionEvent event) {
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


        Button chooseButton = new Button("Выбрать");
        chooseButton.setOnAction(event -> onChooseButtonClick(ingredient));
        chooseButton.setAlignment(Pos.CENTER);
        chooseButton.setPrefWidth(102);
        chooseButton.setPrefHeight(34);
        HBox.setMargin(chooseButton, textInsets);
        chooseButton.setTextAlignment(TextAlignment.CENTER);

        hbox.getChildren().addAll(nameText, caloriesText, proteinText, fatsText, carbsText, chooseButton);
        return hbox;
    }

    private void onChooseButtonClick(Ingredient ingredient) {
        boolean success = selectedRecipe.addNewIngredientWithQuantity(ingredient, 100);

        if (success) {
            System.out.println(ingredient.getName() + " был добавлен в " + selectedRecipe.getName());
        } else {
            // Обработка ошибки (например, ингредиент уже добавлен в рецепт)
            System.out.println("Ошибка при добавлении ингредиента в рецепт");
        }
    }

}
