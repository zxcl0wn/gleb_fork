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

    public Ingredient100Menu(String name, int quantity){
        this.name = name;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "name='" + name + "', quantity=" + quantity;
    }

}