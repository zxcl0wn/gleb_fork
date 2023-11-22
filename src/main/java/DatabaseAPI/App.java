package DatabaseAPI;


import java.sql.*;

public class App {

    public static void main(String[] args) throws SQLException {
//        IngredientDB i1 = new IngredientDB();
//        RecipeDB r1 = new RecipeDB();
//        r1.initTable();


        DBConfig.ConfigDB(true);
        System.out.println("ok");

//        System.out.println(i1.countLines());
//
//        i1.createTableIfNotExists();
//        i1.create("Potato", 10.5,2.23,3.22,1.8);
////        i1.create("2334r32", 3,2,2,2);
////        i1.create("2334r3", 4,2,2,2);
//
//        i1.update(2, "ASD", 77, 0, 0, 0);
////        i1.delete(3);
////        i1.delete(4);
////        i1.delete(5);
        IngredientDB i1 = new IngredientDB();

        ResultSet rs = i1.readAll();
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            double calories = rs.getDouble("calories");
            double protein = rs.getDouble("protein");
            double fats = rs.getDouble("fats");
            double carbs = rs.getDouble("carbs");
            System.out.println(name + " " + calories);

        }


    }
}