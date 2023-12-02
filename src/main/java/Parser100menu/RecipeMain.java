package Parser100menu;

import DatabaseAPI.DB_BaseAbstract;
import DatabaseAPI.IngredientDB;
import RecipeManagerEntities.Ingredient;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

public class RecipeMain {
    public static void main(String[] args) throws IOException {
        String grill_meat = "https://1000.menu/cooking/37536-jarenoe-myaso-na-skovorode";
        String medovik = "https://1000.menu/cooking/53668-tort-medovik-zavarnoi-klassicheskii";
        String kimchi = "https://1000.menu/cooking/63146-kimchi-po-koreiski-iz-pekinskoi-kapusty";
        String rice = "https://1000.menu/cooking/2493-postnye-rolly-s-risom-i-ogurcami";
        String crispy_salty_cucumbers_for_winter_without_vinegar = "https://1000.menu/cooking/13467-xrustyashchie-solenye-ogurcy-na-zimu-bez-uksusa";
        String sour_salty_cucumbers_for_winter_in_jars_without_vinegar ="https://1000.menu/cooking/48686-kislye-solenye-ogurcy-na-zimu-v-bankax-bez-uksusa";
        String salate = "https://1000.menu/cooking/7637-kurinji-salat-s-pomidorom-i-sjrom";
        RecipeParser parser1 = new RecipeParser(rice);
//        Recipe100Menu recipe_by_url = parser1.getRecipeByUrl();
        System.out.println(parser1.getRecipeByUrl());
//        System.out.println(recipe_by_url.img);
//
//        IngredientDB qq = new IngredientDB();
//        qq.createTableIfNotExists();
//
//        AllCPFC t1 = new AllCPFC();
//        Vector<Map> all_ingrid = t1.table_CPFC();
//
////        System.out.println(all_ingrid);
//        for (int i=0; i<all_ingrid.size(); i++) {
//            String i_name = all_ingrid.get(i).get("name").toString();
//            Double i_calories = Double.valueOf((String) all_ingrid.get(i).get("calories"));
//            Double i_proteins = Double.valueOf((String) all_ingrid.get(i).get("proteins"));
//            Double i_fats = Double.valueOf((String) all_ingrid.get(i).get("fats"));
//            Double i_carbohydrates = Double.valueOf((String) all_ingrid.get(i).get("carbohydrates"));
//
//            System.out.println(Ingredient.addToDBAndGet(i_name, i_calories, i_proteins, i_fats, i_carbohydrates));
//        }
//        System.out.println(all_ingrid.size());
    }
}
