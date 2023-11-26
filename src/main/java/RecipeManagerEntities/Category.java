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


    private static List<Category> getCategoriesByResultSet(ResultSet rs) throws SQLException {
        List<Category> list = new LinkedList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            list.add(new Category(id, name));
        }
        return list;
    }

    public static List<Category> getAllCategoriesList() throws SQLException {
        return Category.getCategoriesByResultSet(Category.getAllCategoriesResultSet());
    }

    public static Category getCategoryById(int id) throws SQLException {
        List<Category> c1 =  Category.getCategoriesByResultSet(Category.getCategoryByIdResultSet(id));
        if (c1.isEmpty()) return null;
        return c1.getFirst();
    }

    public static Category getCategoryByName(String name) throws SQLException {
        List<Category> c1 =  Category.getCategoriesByResultSet(Category.getCategoryByNameResultSet(name));
        if (c1.isEmpty()) return null;
        return c1.getFirst();
    }

    public static boolean checkIsInDB(String name) throws SQLException {
        Category c1 = getCategoryByName(name);
        return !Objects.isNull(c1);
    }

    public static Category addToDBAndGet(String name) throws SQLException {
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

    public boolean setName(String name) throws SQLException {
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




