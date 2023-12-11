package com.example.fefu_javafx_2;

import RecipeManagerEntities.Ingredient;
import RecipeManagerEntities.IngredientWithQuantity;
import RecipeManagerEntities.Recipe;
import RecipeManagerEntities.RecipeStep;
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

public class AddStepController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Recipe selectedRecipe;
    public TextField imageLink;
    public TextField description;
    private ViewPageController viewPageController;

    public void closeButton(javafx.event.ActionEvent event) {
        Stage stageToClose = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stageToClose.close();
    }

    public void setRecipe(Recipe recipe) {
        this.selectedRecipe = recipe;
    }

    public void addStepButton(javafx.event.ActionEvent event) {
        String stepText = description.getText().trim();
        String stepImg = imageLink.getText().trim();

        if (!stepText.isEmpty()) {
            RecipeStep newStep = RecipeStep.addToDBAndGet(selectedRecipe.getId(), stepText, stepImg);
            if (newStep != null) {
                System.out.println("Шаг успешно добавлен: " + newStep.getText());
                ViewPageController viewPageController = getParentController();

                closeButton(event);
            } else {
                System.out.println("Ошибка при добавлении шага");
            }
        } else {
            System.out.println("Шаг не может быть пустым");
        }
    }


    public void setViewPageController(ViewPageController viewPageController) {
        this.viewPageController = viewPageController;
    }
    private ViewPageController getParentController() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("view_page.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loader.getController();
    }
}
