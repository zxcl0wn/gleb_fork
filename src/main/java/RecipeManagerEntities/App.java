package RecipeManagerEntities;


import java.sql.SQLException;
import java.util.List;

public class App {

    public static void main(String[] args) throws SQLException {
       List<Favorite> list_of_f = Favorite.getAllFavoritesList();

       System.out.println(list_of_f.size());

        for (Favorite f1 : list_of_f) {
            System.out.println(f1.toString());
        }

        System.out.println(Ingredient.addToDBAndGet("OO1", 55, 0, 0, 0));







    }
}