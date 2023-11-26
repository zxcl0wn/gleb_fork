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
    public double carbohydrates;
    public double fats;
    public double proteins;
    public double calories;

    public Ingredient100Menu(String name, int quantity, double carbohydrates, double fats, double proteins, double calories) {
        this.name = name;
        this.quantity = quantity;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
        this.proteins = proteins;
        this.calories = calories;
    }

    @Override
    public String toString() {
        return String.format("name='%s', quantity=%d, carbohydrates=%.2f, fats=%.2f, proteins=%.2f, calories=%.2f",
                name, quantity, carbohydrates, fats, proteins, calories / 1000.0);
    }


}