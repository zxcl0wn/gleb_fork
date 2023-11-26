package RecipeManagerEntities;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import DatabaseAPI.CategoriesDB;


public class Category {
    private int id;
    public String name;

    private Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public static ResultSet getAllCategoriesResultSet(){
        CategoriesDB categories_db = new CategoriesDB();
        return categories_db.readAll();
    }

    public static List<Category> getCategoriesByResultSet(ResultSet rs) throws SQLException {
        List<Category> list = new LinkedList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");

            list.add(new Category(id, name));
        }
        return list;
    }

    public static Category addToDBAndGet(String name) throws SQLException {
        List<Category> all_categories = Category.getCategoriesByResultSet(Category.getAllCategoriesResultSet());
        for (Category category: all_categories) {
            if (Objects.equals(category.name, name)){
                return category;
            }
        }

        CategoriesDB categories_db = new CategoriesDB();
        categories_db.create(name);
        all_categories = Category.getCategoriesByResultSet(Category.getAllCategoriesResultSet());
        return all_categories.getLast();
    }

    public void updateInDB() {
        CategoriesDB categories_db = new CategoriesDB();
        categories_db.update(id, name);
    }

    public void delete() {
        CategoriesDB categories_db = new CategoriesDB();
        categories_db.delete(id);
    }


}




