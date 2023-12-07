package com.example.fefu_javafx_2;

import RecipeManagerEntities.RecipeStep;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ChangeStepController {
    public TextField stepTextField;
    public AnchorPane changeButton;
    public Label stepLabel;
    public TextField imageTextField;
    private RecipeStep recipeStep;

    public void changeStep(ActionEvent event) {
        String newStepText = stepTextField.getText().trim();

        // Проверяем, что новый текст не пустой
        if (!newStepText.isEmpty()) {
            // Устанавливаем новый текст в объект recipeStep
            recipeStep.setText(newStepText);
            // Выводим в консоль сообщение об успешном изменении
            System.out.println("Название шага успешно изменено: " + newStepText);
        } else {
            // Если новый текст пустой, выводим сообщение об ошибке
            System.out.println("Ошибка: Название шага не может быть пустым");
        }
    }

    public void setStep(RecipeStep recipeStep) {
        this.recipeStep = recipeStep;
        System.out.println(recipeStep);

        stepTextField.setText(recipeStep.getText());
        imageTextField.setText(recipeStep.getImg());
    }

    public void changeImage(ActionEvent event) {
        String newImageText = imageTextField.getText().trim();

        // Устанавливаем новый текст для изображения в объект recipeStep
        recipeStep.setImg(newImageText);

        // Выводим в консоль сообщение об успешном изменении
        System.out.println("Изображение шага успешно изменено: " + newImageText);
    }
}
