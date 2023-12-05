package com.example.fefu_javafx_2;

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
import RecipeManagerEntities.Category;
public class AddCategoryController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField categoryNameField;

    @FXML
    public void add_new_category(javafx.event.ActionEvent actionEvent) throws IOException {
        String categoryName = categoryNameField.getText();
        Category newCategory = Category.addToDBAndGet(categoryName);

        if (newCategory != null) {
            System.out.println("Категория успешно добавлена: " + newCategory.getName());
        } else {
            System.out.println("Категория уже существует или произошла ошибка при добавлении.");
        }

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
