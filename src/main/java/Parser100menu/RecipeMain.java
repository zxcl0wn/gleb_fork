package Parser100menu;

import RecipeManagementEntities.RecipeIngredient;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
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
        RecipeParser parser1 = new RecipeParser(medovik);
        System.out.println(parser1.getRecipeByUrl());
//        AllCPFC t1 = new AllCPFC();
//        System.out.println(t1.table_CPFC());
    }
}
