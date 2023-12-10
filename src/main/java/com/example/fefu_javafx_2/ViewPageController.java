package com.example.fefu_javafx_2;

import RecipeManagerEntities.IngredientWithQuantity;
import RecipeManagerEntities.Recipe;
import RecipeManagerEntities.RecipeStep;
import RecipeManagerEntities.ShoppingCart;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
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
    public VBox VboxSteps;
    public ImageView main_img;
    public Button switchChangeImgButton;
    private Recipe selectedRecipe;


    private Stage stage;
    private Scene scene;
    private Parent root;

    public void setRecipe(Recipe recipe) {
        System.out.println("SET RECIPE!");
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

        String imagePath = recipe.getImg();
        if (imagePath != null && !imagePath.isEmpty()) {
            Image image = new Image("file:" + imagePath);
            main_img.setImage(image);
        }

        setIngredients(recipe.getIngredientsWithQuantity());
        setRecipeSteps(recipe.getId());
    }

    private void setIngredients(List<IngredientWithQuantity> ingredientsWithQuantities) {
        VboxIngredients.getChildren().clear();
        VboxIngredients.setSpacing(50);

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
        switchChangeImgButton.setOnAction(event -> {
            try {
                switch_change_img(selectedRecipe);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
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
            addIngredientInRecipeController controller = loader.getController();

            controller.setSelectedRecipe(recipe);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void switch_add_step(Recipe recipe) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("add_step.fxml"));
            Parent root = loader.load();
            AddStepController controller = loader.getController();

            controller.setRecipe(recipe);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setRecipeSteps(int recipeId) {
        VboxSteps.getChildren().clear();
        VboxSteps.setSpacing(10);
        VboxSteps.setPadding(new Insets(20));

        List<RecipeStep> recipeSteps = RecipeStep.getRecipeStepsByRecipeId(recipeId);

        for (RecipeStep recipeStep : recipeSteps) {
            HBox stepHBox = createStepHBox(recipeStep);
            VboxSteps.getChildren().add(stepHBox);
        }

        HBox stepHbox = new HBox(30);
        stepHbox.setAlignment(Pos.CENTER);
        Button addStepButton = new Button("Добавить шаг");
        addStepButton.setFont(new Font(23));
        stepHbox.getChildren().add(addStepButton);
        addStepButton.setOnAction(event -> {
            try {
                switch_add_step(selectedRecipe);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        VboxSteps.getChildren().add(stepHbox);

    }

    private HBox createStepHBox(RecipeStep recipeStep) {
        HBox stepHBox = new HBox(150);
        stepHBox.setSpacing(50);
        ImageView stepImage = new ImageView("file:///" + recipeStep.getImg());
        stepImage.setFitHeight(150);
        stepImage.setFitWidth(150);

        Text stepText = new Text(recipeStep.getText());
        stepText.setFont(new Font(22));

        Button deleteButton = new Button("Удалить");
        deleteButton.setOnAction(event -> onDeleteStepButtonClick(recipeStep));

        Button changeStepButton = new Button("Изменить шаг");
        changeStepButton.setOnAction(event -> onChangeStepButtonClick(recipeStep));

        stepHBox.getChildren().addAll(stepImage, stepText, deleteButton, changeStepButton);

        return stepHBox;
    }

    private void onDeleteStepButtonClick(RecipeStep recipeStep) {
        recipeStep.delete();
//        System.out.println("УДАЛЕНИЕ РЕЦ");
        setRecipeSteps(selectedRecipe.getId());
    }

    private void onChangeStepButtonClick(RecipeStep recipeStep) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("change_step.fxml"));
            Parent root = loader.load();

            ChangeStepController changeStepController = loader.getController();
            stage = new Stage();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            changeStepController.setStep(recipeStep);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add_to_cart(ActionEvent event) {
        ShoppingCart.addIngredientsToShoppingCartByRecipe(selectedRecipe);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успех");
        alert.setHeaderText(null);
        alert.setContentText("Ингредиенты успешно добавлены в корзину покупок!");
        alert.showAndWait();
    }

    public void DeleteRecipe(ActionEvent event) {
        this.selectedRecipe.delete();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void switch_change_img(Recipe recipe) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("change_img.fxml"));
            Parent root = loader.load();
            ChangeImgController controller = loader.getController();

            controller.setRecipe(recipe);
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
