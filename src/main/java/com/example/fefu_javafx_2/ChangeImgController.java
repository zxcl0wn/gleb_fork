package com.example.fefu_javafx_2;

import RecipeManagerEntities.Recipe;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ChangeImgController {
    public TextField imageLink;
    private Recipe selectedRecipe;
    public void closeButton(javafx.event.ActionEvent event) {
        Stage stageToClose = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stageToClose.close();
    }

    public void addChangeButton(ActionEvent event) {
//        if (imageLink != null && !imageLink.isEmpty()) {
//            Image image = new Image("file:" + imageLink);
//            main_img.setImage(image);
//        }
        String newImgageLink = imageLink.getText();

        if (!newImgageLink.isEmpty()) {
//            recipeStep.setText(newImgageLink);
            selectedRecipe.setImg(newImgageLink);
            System.out.println("Изображение успешно изменено: " + newImgageLink);
        } else {
            System.out.println("Ошибка");
        }
    }

    public void setRecipe(Recipe recipe) {
        this.selectedRecipe = recipe;
    }
}
