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


}




