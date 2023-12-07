package com.example.fefu_javafx_2;

import RecipeManagerEntities.Ingredient;
import RecipeManagerEntities.IngredientWithQuantity;
import RecipeManagerEntities.Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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

        HBox buttonHbox = new HBox(30);
        buttonHbox.setAlignment(Pos.CENTER);
        Button switchAddIngredientButton = new Button("Добавить");
        switchAddIngredientButton.setFont(new Font(23));
        buttonHbox.getChildren().add(switchAddIngredientButton);
        switchAddIngredientButton.setOnAction(event -> {
            try {
                switch_add_ingredient_in_recipe(selectedRecipe);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        VboxIngredients.getChildren().add(buttonHbox);
    }
    private HBox createIngredientHBox(IngredientWithQuantity ingredientWithQuantity) {
        HBox ingredientHBox = new HBox(30);

        Text ingredientText = new Text(ingredientWithQuantity.getIngredient().getName() + ": " + ingredientWithQuantity.getQuantity() + " грамм");
        ingredientText.setFont(new Font(22));

        Button deleteButton = new Button("Удалить");
        deleteButton.setOnAction(event -> onDeleteButtonClick(ingredientWithQuantity));

        Button changeButton = new Button("Изменить");
        changeButton.setOnAction(event -> onChangeButtonClick(ingredientWithQuantity));
        ingredientHBox.getChildren().addAll(ingredientText, deleteButton, changeButton);

        return ingredientHBox;
    }

    private void onDeleteButtonClick(IngredientWithQuantity ingredientWithQuantity) {
//        System.out.println("Deleting Ingredient: " + ingredientWithQuantity.getIngredient().getName());
        ingredientWithQuantity.delete();
    }

    public void onChangeButtonClick(IngredientWithQuantity ingredientWithQuantity) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("change_quantity_ingredient.fxml"));
            Parent root = loader.load();

            ChangeQuantityIngredientController changeQuantityIngredientController = loader.getController();

            changeQuantityIngredientController.setIngredient(ingredientWithQuantity);
//            System.out.println(ingredientWithQuantity);
            stage = new Stage();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @FXML
    public void switch_add_ingredient_in_recipe(Recipe recipe) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add_ingredient_in_recipe.fxml"));
            Parent root = loader.load();
            addIngredientInRecipe controller = loader.getController();

            controller.setSelectedRecipe(recipe);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
