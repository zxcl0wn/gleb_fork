package com.example.fefu_javafx_2;

import RecipeManagerEntities.Ingredient;
import RecipeManagerEntities.RecipeIngredient;
import RecipeManagerEntities.ShoppingCart;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class CartController implements Initializable {
    public VBox IngredientsVbox;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void switch_home(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main_page.fxml"));
        stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadIngredientsFromCart();
    }
    private void loadIngredientsFromCart() {
        Map<String, Double> ingredientsMap = ShoppingCart.getIngredientsMap();
        IngredientsVbox.setSpacing(10);
        for (Map.Entry<String, Double> entry : ingredientsMap.entrySet()) {
            String ingredientName = entry.getKey();
            Double quantity = entry.getValue();

            HBox ingredientHBox = createIngredientHBox(ingredientName, quantity);
            IngredientsVbox.getChildren().add(ingredientHBox);
        }
    }

    private HBox createIngredientHBox(String ingredientName, Double quantity) {
        HBox ingredientHBox = new HBox();
        ingredientHBox.setSpacing(80);

        Text nameText = new Text(ingredientName);
        nameText.setFont(new Font(20));

        Text quantityText = new Text(quantity + " г");
        quantityText.setFont(new Font(20));

        Button deleteButton = new Button("Удалить");
        deleteButton.setOnAction(event -> onDeleteButtonClick(ingredientName));
        deleteButton.setFont(new Font(20));

        ingredientHBox.getChildren().addAll(nameText, quantityText, deleteButton);
        return ingredientHBox;
    }

    private void onDeleteButtonClick(String ingredientName) {
        Ingredient ingredient = Ingredient.getIngredientByName(ingredientName);

        if (ingredient != null) {
            // Получаем список RecipeIngredient, связанных с данным ингредиентом
            List<RecipeIngredient> recipeIngredients = RecipeIngredient.getRecipesIngredientsByIngredientIdList(ingredient.getId());

            // Удаляем соответствующие записи из корзины
//            for (RecipeIngredient recipeIngredient : recipeIngredients) {
            ShoppingCart.deleteByIngredientId(ingredient.getId());


            // Обновляем отображение ингредиентов в корзине
//            loadIngredientsFromCart();
        }
    }


}
