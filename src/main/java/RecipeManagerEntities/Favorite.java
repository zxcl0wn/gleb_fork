package RecipeManagerEntities;

import DatabaseAPI.FavoritesDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Favorite {
    private int id;
    private int recipe_id;

    private Favorite(int id, int recipe_id) {
        this.id = id;
        this.recipe_id = recipe_id;
    }

    private static ResultSet getAllFavoritesResultSet(){
        FavoritesDB favorites_db = new FavoritesDB();
        return favorites_db.readAll();
    }

    private static ResultSet getAllFavoriteByRecipeIdResultSet(int recipe_id){
        FavoritesDB favorites_db = new FavoritesDB();
        return favorites_db.readByRecipeId(recipe_id);
    }

    private static List<Favorite> getFavoritesByResultSet(ResultSet rs) throws SQLException {
        List<Favorite> list = new LinkedList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            int recipe_id = rs.getInt("recipe_id");

            list.add(new Favorite(id, recipe_id));
        }
        return list;
    }

    public static List<Favorite> getAllFavoritesList() throws SQLException {
        return Favorite.getFavoritesByResultSet(Favorite.getAllFavoritesResultSet());
    }

    public static Favorite getFavoriteByRecipeId(int recipe_id) throws SQLException {
        List<Favorite> f1 = Favorite.getFavoritesByResultSet(Favorite.getAllFavoriteByRecipeIdResultSet(recipe_id));
        if (f1.isEmpty()) return null;
        return f1.getFirst();
    }

    public static boolean checkIsInDB(int recipe_id) throws SQLException {
        Favorite f1 = getFavoriteByRecipeId(recipe_id);
        return !Objects.isNull(f1);
    }

    public static Favorite addToDBAndGet(int recipe_id) throws SQLException {
        if (checkIsInDB(recipe_id)) return null;
        FavoritesDB favorites_db = new FavoritesDB();
        favorites_db.create(recipe_id);

        return getFavoriteByRecipeId(recipe_id);
    }

    public void delete() {
        FavoritesDB favorites_db = new FavoritesDB();
        favorites_db.delete(id);
        id = -1;
        recipe_id = -1;
    }

    public int getId() {
        return id;
    }

    public int getRecipeId() {
        return recipe_id;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "id=" + id +
                ", recipe_id=" + recipe_id +
                '}';
    }
}


