package com.example.fefu_javafx_2;

import RecipeManagerEntities.Ingredient;
import RecipeManagerEntities.IngredientWithQuantity;
import RecipeManagerEntities.Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ViewPageController implements Initializable{
    public Text textCategory;
    public Label recipeNameLabel;
    public VBox recipeVboxIngredients;
    public Text textCalories;
    public Text textCookingTime;
    public Text textLevel;
    public Text textProtein;
    public Text textFats;
    public Text textCarbs;
    public VBox recipeVbox;
    public VBox VboxIngredients;
    private Recipe selectedRecipe;


    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setRecipe(Recipe recipe) {
        this.selectedRecipe = recipe;
        System.out.println(this.selectedRecipe);

        recipeNameLabel.setText(selectedRecipe.getName());
        textCategory.setText(selectedRecipe.getCategory().getName());
        textCalories.setText(Double.toString(selectedRecipe.getCaloriesOfRecipe()) + " калорий");
        textCookingTime.setText(selectedRecipe.getCookingTime());
        textLevel.setText("Сложность: " + selectedRecipe.getDifficultyLevel().getName());
        textProtein.setText(Double.toString(selectedRecipe.getProteinOfRecipe()) + " белков");
        textFats.setText(Double.toString(selectedRecipe.getFatsOfRecipe()) + " жиров");
        textCarbs.setText(Double.toString(selectedRecipe.getCarbsOfRecipe()) + " углеводов");

        setIngredients(recipe.getIngredientsWithQuantity());
    }

    private void setIngredients(List<IngredientWithQuantity> ingredientsWithQuantities) {
        VboxIngredients.getChildren().clear();
        VboxIngredients.setSpacing(20);

        for (IngredientWithQuantity ingredientWithQuantity : ingredientsWithQuantities) {
            HBox ingredientHBox = createIngredientHBox(ingredientWithQuantity);
            VboxIngredients.getChildren().add(ingredientHBox);
        }
    }
    private HBox createIngredientHBox(IngredientWithQuantity ingredientWithQuantity) {
        HBox ingredientHBox = new HBox(30);

        Text ingredientText = new Text(ingredientWithQuantity.getIngredient().getName() + ": " + ingredientWithQuantity.getQuantity() + " грамм");
        ingredientText.setFont(new Font(22));

        Button deleteButton = new Button("Удалить");
        deleteButton.setOnAction(event -> onDeleteButtonClick(ingredientWithQuantity));

        ingredientHBox.getChildren().addAll(ingredientText, deleteButton);

        return ingredientHBox;
    }

    private void onDeleteButtonClick(IngredientWithQuantity ingredientWithQuantity) {
//        System.out.println("Deleting Ingredient: " + ingredientWithQuantity.getIngredient().getName());
        ingredientWithQuantity.delete();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
//        recipeNameLabel.setText(selectedRecipe.getName());
    }

    @FXML
    public void switch_home(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main_page.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
