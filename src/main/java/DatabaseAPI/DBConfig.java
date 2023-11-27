package DatabaseAPI;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConfig {
    public static String url = "jdbc:postgresql://localhost:5432/food_project";
    public static String username = "postgres";
    public static String password = "123";

    public static void ConfigDB(boolean add_data) {
        ConfigIngredientsDB(add_data);
        ConfigCategoriesDB(add_data);
        ConfigLevelsOfDifficultyDB(add_data);
        ConfigRecipeDB(add_data);
        ConfigRecipeStepsDB(add_data);
        ConfigFavoritesDB(add_data);
        ConfigRecipesIngredientsDB(add_data);
        ConfigShoppingCartDB(add_data);

        }

    private static void ConfigIngredientsDB(boolean add_data) {
        IngredientDB i1 = new IngredientDB();
        i1.initTable();
        if (add_data && i1.countLines() == 0) {
            i1.create("Жир говяжий топленый", 871, 0, 100, 0);
            i1.create("Говядина жирная", 171, 20, 23, 0);
            i1.create("Сыр «советский»", 400, 24, 31, 0);
            i1.create("Сыр «аиадеус»", 364, 21, 26, 2);
            i1.create("Киви", 48, 1, 1, 10);
            i1.create("Рябина черноплодная", 52, 2, 0, 14);
            i1.create("Кишмиш", 279, 2, 0, 71);
            i1.create("Женьшень", 41, 0, 0, 10);
            i1.create("Лесные грибы", 33, 3, 1, 4);
            i1.create("Мясо кабана", 122, 22, 3, 0);
            i1.create("Цветки липы", 0, 0, 0, 0);
        }
    }

    private static void ConfigCategoriesDB(boolean add_data) {
        CategoriesDB c1 = new CategoriesDB();
        c1.initTable();
        if (add_data && c1.countLines() == 0) {
            c1.create("Салат Цезарь");
            c1.create("Грибы");
            c1.create("Борщ");
            c1.create("Морковь с луком");
            c1.create("Тесто для пельменей");
            c1.create("Цветочный букет");
            c1.create("Высокая кухня");
        }
    }

    private static void ConfigLevelsOfDifficultyDB(boolean add_data) {
        LevelsOfDifficultyDB l1 = new LevelsOfDifficultyDB();
        l1.initTable();
        if (add_data && l1.countLines() == 0) {
            l1.create("Easy");
            l1.create("Medium");
            l1.create("Hard");
            l1.create("Unreal");
            l1.create("Super Unreal");
        }
    }

    private static void ConfigRecipeDB(boolean add_data) {
        RecipeDB r1 = new RecipeDB();
        r1.initTable();
        if (add_data && r1.countLines() == 0) {
            r1.create("Салат из лесных цветов с грибами", 6, "2", "30 мин", 1);
            r1.create("Булка с маком", 7, "2", "45 мин", 2);
            r1.create("Грибы с сыром", 2, "2", "2 ч 30 мин", 3);
            r1.create("Креветки с ягодами", 7, "2", "1 ч", 3);
            r1.create("Жаренные томаты", 4, "2", "5 мин", 4);
            r1.create("Борщ с женьшенем", 3, "2", "3 ч", 3);
            r1.create("Постный жир с тестом", 5, "2", "10 ч", 1);
            r1.create("Утка с грибами", 2, "2", "2 ч 30 мин", 2);
            r1.create("Гриб с грибами", 2, "2", "20 мин", 1);
            r1.create("Чай из ягод", 7, "2", "5 ч", 5);
        }
    }

    private static void ConfigRecipeStepsDB(boolean add_data) {
        RecipeStepsDB rs1 = new RecipeStepsDB();
        rs1.initTable();
        if (add_data && rs1.countLines() == 0) {
            rs1.create(9, "Шаг 1. Возмите гриб", "2");
            rs1.create(9, "Шаг 2. Возмите ещё 1 один гриб", "2");
            rs1.create(9, "Шаг 3. Возмите ещё 1 один гриб", "2");
            rs1.create(9, "Шаг 4. Поместите грибы внутрь гриба", "2");
            rs1.create(9, "Шаг 5. Перемещайте получившуюся смесь", "2");
            rs1.create(9, "Шаг 6. Подовать с грибами", "2");

            rs1.create(2, "Шаг 1. Положите булку с маком в духовку", "2");
            rs1.create(2, "Шаг 2. Соль и перец по вкусы", "2");
            rs1.create(2, "Шаг 3. Можно есть", "2");

        }
    }

    private static void ConfigFavoritesDB(boolean add_data) {
        FavoritesDB f1 = new FavoritesDB();
        f1.initTable();
        if (add_data && f1.countLines() == 0) {
            f1.create(1);
            f1.create(3);
            f1.create(6);
            f1.create(4);

        }
    }

    private static void ConfigRecipesIngredientsDB(boolean add_data) {
        RecipesIngredientsDB ri1 = new RecipesIngredientsDB();
        ri1.initTable();
        if (add_data && ri1.countLines() == 0) {
            ri1.create(9, 9, 300);
            ri1.create(9, 10, 100);

            ri1.create(1, 9, 200);
            ri1.create(1, 11, 100);
            ri1.create(1, 8, 4000);
            ri1.create(1, 7, 30);

            ri1.create(6, 8, 200);
            ri1.create(6, 9, 250);
            ri1.create(6, 1, 600);
            ri1.create(6, 2, 150);
            ri1.create(6, 3, 25);
        }
    }

    private static void ConfigShoppingCartDB(boolean add_data) {
        ShoppingCartDB cs1 = new ShoppingCartDB();
        cs1.initTable();
        if (add_data && cs1.countLines() == 0) {
            cs1.create(1);
            cs1.create(2);
            cs1.create(5);
        }
    }



}




