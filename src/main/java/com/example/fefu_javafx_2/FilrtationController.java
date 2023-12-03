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
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FilrtationController implements Initializable{
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ComboBox<String> ComboBoxLevel;
    @FXML
    private ComboBox<String> ComboBoxTime;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        ComboBoxLevel.getItems().addAll("Easy", "Medium", "Hard", "Unreal", "Super Unreal");
        ComboBoxTime.getItems().addAll("До 1 часа", "1-3 часа", "3-5 часов", "5-15 часов", "15-23 часов", "24+ часов");
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
