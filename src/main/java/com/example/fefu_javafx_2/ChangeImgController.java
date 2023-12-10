package com.example.fefu_javafx_2;

import RecipeManagerEntities.Recipe;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ChangeImgController {
    private Recipe selectedRecipe;
    private ViewPageController viewPageController;

    public TextField imageLink;
    public void closeButton(javafx.event.ActionEvent event) {
        Stage stageToClose = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageToClose.close();
    }
    public void setViewPageController(ViewPageController viewPageController) {
        this.viewPageController = viewPageController;
    }

    public void addChangeButton(ActionEvent event) {
        String newImgageLink = imageLink.getText();

        if (!newImgageLink.isEmpty()) {
            selectedRecipe.setImg(newImgageLink);

            viewPageController.setRecipe(selectedRecipe);

            closeButton(event);
            System.out.println("Изображение успешно изменено: " + newImgageLink);
        } else {
            closeButton(event);
            System.out.println("Ошибка");
        }
    }

    public void setRecipe(Recipe recipe) {
        this.selectedRecipe = recipe;
    }
}
