package RecipeManagerEntities;

import DatabaseAPI.FavoritesDB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Favorite {
    private final int id;
    private final int recipe_id;

    private Favorite(int id, int recipe_id) {
        this.id = id;
        this.recipe_id = recipe_id;
    }

    public static ResultSet getAllFavoritesResultSet(){
        FavoritesDB favorites_db = new FavoritesDB();
        return favorites_db.readAll();
    }

    public static List<Favorite> getFavoritesByResultSet(ResultSet rs) throws SQLException {
        List<Favorite> list = new LinkedList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            int recipe_id = rs.getInt("recipe_id");

            list.add(new Favorite(id, recipe_id));
        }
        return list;
    }

    public static boolean checkIsInDB(int recipe_id) throws SQLException {
        List<Favorite> all_favorites = Favorite.getFavoritesByResultSet(Favorite.getAllFavoritesResultSet());
        for (Favorite f1: all_favorites) {
            if (Objects.equals(f1.recipe_id, recipe_id)){
                return true;
            }
        }
        return false;
    }

    public static Favorite addToDBAndGet(int recipe_id) throws SQLException {
        List<Favorite> all_favorites = Favorite.getFavoritesByResultSet(Favorite.getAllFavoritesResultSet());
        for (Favorite f1: all_favorites) {
            if (f1.recipe_id == recipe_id){
                return f1;
            }
        }

        FavoritesDB favorites_db = new FavoritesDB();
        favorites_db.create(recipe_id);
        all_favorites = Favorite.getFavoritesByResultSet(Favorite.getAllFavoritesResultSet());

        Favorite last_favorite = all_favorites.getLast();
        if (last_favorite.recipe_id != recipe_id) return null; // если рецепта с таким id не существует
        return last_favorite;
    }

    public void delete() {
        FavoritesDB favorites_db = new FavoritesDB();
        favorites_db.delete(id);
    }

    public int getId() {
        return id;
    }

    public int getRecipe_id() {
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


