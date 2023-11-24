package Parser100menu;

import java.io.IOException;

public class RecipeMain {
    public static void main(String[] args) throws IOException {
        String grill_meat = "https://1000.menu/cooking/37536-jarenoe-myaso-na-skovorode";
        String medovik = "https://1000.menu/cooking/53668-tort-medovik-zavarnoi-klassicheskii";
        String kimchi = "https://1000.menu/cooking/63146-kimchi-po-koreiski-iz-pekinskoi-kapusty";

        RecipeParser parser1 = new RecipeParser(kimchi);
//        System.out.println(parser1.name);
//        System.out.println(parser1.category);
//        System.out.println(parser1.img);
//        System.out.println(parser1.cooking_time);
//
//        parser1.ParserIngredients();
//        System.out.println(Arrays.toString(parser1.all_ingredients));
//
//        parser1.ParserSteps();
//        System.out.println(Arrays.toString(parser1.all_steps));
        System.out.println(parser1.getRecipeByUrl());
    }
}
