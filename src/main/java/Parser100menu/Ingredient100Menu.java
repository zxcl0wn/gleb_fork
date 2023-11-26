package Parser100menu;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;


public class Ingredient100Menu {
    public String name;
    public int quantity;

    public double calories;
    public double protein;
    public double fats;
    public double carbs;

    public Ingredient100Menu(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
    }

    //        TODO: Gleb
    public Ingredient100Menu(String name, int quantity, double calories, double protein, double fats, double carbs){
        this.name = name;
        this.quantity = quantity;
        this.calories = calories;
        this.protein = protein;
        this.fats = fats;
        this.carbs = carbs;
    }



    @Override
    public String toString() {
        return "name='" + name + "', quantity=" + quantity;
    }

}