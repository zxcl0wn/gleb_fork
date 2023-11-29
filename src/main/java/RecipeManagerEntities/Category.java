package RecipeManagerEntities;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import DatabaseAPI.CategoriesDB;


public class Category {
    private int id;
    private String name;

    private Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    private static ResultSet getAllCategoriesResultSet(){
        CategoriesDB categories_db = new CategoriesDB();
        return categories_db.readAll();
    }

    private static ResultSet getCategoryByIdResultSet(int id){
        CategoriesDB categories_db = new CategoriesDB();
        return categories_db.read(id);
    }

    public static ResultSet getCategoryByNameResultSet(String name){
        CategoriesDB categories_db = new CategoriesDB();
        return categories_db.readByName(name);
    }


    private static List<Category> getCategoriesByResultSet(ResultSet rs) {
        List<Category> list = new LinkedList<>();
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                list.add(new Category(id, name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public static List<Category> getAllCategoriesList() {
        return Category.getCategoriesByResultSet(Category.getAllCategoriesResultSet());
    }

    public static Category getCategoryById(int id) {
        List<Category> c1 =  Category.getCategoriesByResultSet(Category.getCategoryByIdResultSet(id));
        if (c1.isEmpty()) return null;
        return c1.getFirst();
    }

    public static Category getCategoryByName(String name) {
        List<Category> c1 =  Category.getCategoriesByResultSet(Category.getCategoryByNameResultSet(name));
        if (c1.isEmpty()) return null;
        return c1.getFirst();
    }

    public static boolean checkIsInDB(int id) {
        Category c1 = getCategoryById(id);
        return !Objects.isNull(c1);
    }

    public static boolean checkIsInDB(String name) {
        Category c1 = getCategoryByName(name);
        return !Objects.isNull(c1);
    }

    public static Category addToDBAndGet(String name) {
        if (checkIsInDB(name)) return null;

        CategoriesDB categories_db = new CategoriesDB();
        categories_db.create(name);
        return Category.getCategoryByName(name);
    }

    private void updateInDB() {
        CategoriesDB categories_db = new CategoriesDB();
        categories_db.update(id, name);
    }

    public void delete() {
        CategoriesDB categories_db = new CategoriesDB();
        categories_db.delete(id);
        id = -1;
        name = "deleted";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean setName(String name) {
        if (checkIsInDB(name)) return false;
        this.name = name;
        this.updateInDB();
        return true;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}




