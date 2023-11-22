package Parser100menu;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;

public class RecipeStep100Menu{
    public String text;
    public String img;

    public RecipeStep100Menu(String text, String img) {
        this.text = text;
        this.img = img;
    }

    @Override
    public String toString() {
        return "text='" + text + "', img=" + img;
    }

}