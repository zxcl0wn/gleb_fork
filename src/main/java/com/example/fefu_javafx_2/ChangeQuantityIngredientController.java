package com.example.fefu_javafx_2;

import RecipeManagerEntities.IngredientWithQuantity;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeQuantityIngredientController {
    public TextField quantityTextField;
    public AnchorPane changeButton;
    public Label ingredientLabel;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private IngredientWithQuantity ingredientWithQuantity;

    public void setIngredient(IngredientWithQuantity ingredientWithQuantity) {
        this.ingredientWithQuantity = ingredientWithQuantity;

        ingredientLabel.setText(ingredientWithQuantity.getIngredient().getName());
        quantityTextField.setText(String.valueOf(ingredientWithQuantity.getQuantity()));
    }


    @FXML
    public void changeQuantityAndClose() {
        try {
            double newQuantity = Double.parseDouble(quantityTextField.getText());

            ingredientWithQuantity.setQuantity(newQuantity);

            Stage stage = (Stage) changeButton.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
