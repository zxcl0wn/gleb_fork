package Parser100menu;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.*;
import java.util.regex.*;
import com.google.gson.Gson;

public class RecipeParser {
    private Document Jsoupdocument;
    public String name;
    public String category;
    public String img;
    public String cooking_time;
    public Ingredient100Menu[] all_ingredients;
    public RecipeStep100Menu[] all_steps;
    public AllCPFC table_CPFC_parse = new AllCPFC();
    public Vector<Map> table_CPFC = table_CPFC_parse.table_CPFC();

//    public int carbohydrates;
//    public int fats;
//    public int proteins;
//    public int calories;

    public RecipeParser(String link) throws IOException {
        this.Jsoupdocument = Jsoup.connect(link).get();
        this.name = Jsoupdocument.select("h1[itemprop=name]").text();
        this.category = Jsoupdocument.select("ol.breadcrumbs a[href*=catalog]").text();
        this.img = Jsoupdocument.select("div.main-photo img[itemprop=image]").attr("abs:src");
        this.cooking_time = Jsoupdocument.select("span.label strong").text();

        Elements steps = Jsoupdocument.select("ol.instructions li");
        String[] steps_array = new String[steps.size() - 1];
        int local_index = 0;
        for (int i = 0; i < steps.size(); i++) {
            String[] one_step = new String[2];
            one_step[0] = steps.get(i).select("p.instruction").text();
            one_step[1] = steps.get(i).select("a[href]").attr("href");
            if (!one_step[0].isEmpty() && !one_step[1].isEmpty()) {
                steps_array[local_index] = Arrays.toString(one_step);
                local_index++;
            }
        }
    }


    public void ParserIngredients() {
        Elements elements_ingredients = Jsoupdocument.select("form#recept-list a[href*=catalog]");
        Pattern pattern = Pattern.compile("var cook_ing_data = \\{.*?\\};");
        Matcher matcher = pattern.matcher(Jsoupdocument.toString());

        String foundString;
        String FoundString2 = null;
        if (matcher.find()) {
            foundString = matcher.group();
            FoundString2 = foundString.substring(20, foundString.length() - 1);
        }

        Gson newFoundString = new Gson();
        Ingredient100Menu[] ingredients_array = new Ingredient100Menu[elements_ingredients.size()];  // Массив инстансов класса Ingredient100Menu
        for (int i = 0; i < ingredients_array.length; i++) {
            Map<String, Object> JsonMap = newFoundString.fromJson(FoundString2, Map.class);  // JSON строка
            List<Map<String, Object>> JsonIngredients = (List<Map<String, Object>>) JsonMap.get("ingredients");  // JSON ингредиенты

            List<Map<String, Object>> JsonMeasures = (List<Map<String, Object>>) JsonMap.get("measures");  // JSON меры
            Object current_measure = JsonIngredients.get(i).get("measure_id");  // measure_id каждого ингредиента
            int measure_int = ((Number) current_measure).intValue();

            Object conversion = null;
            for (Map<String, Object> jsonMeasure : JsonMeasures) {  // Прохожусь по всем measures, чтобы найти соотвестующий
                Object current_id = jsonMeasure.get("id");
                int id_int = ((Number) current_id).intValue();

                if (measure_int == id_int) {
                    conversion = jsonMeasure.get("grams_conversion");
                    if (conversion == null) {
                        conversion = JsonIngredients.get(i).get("grams_in_pce");
                    }
                }
            }


            String IngredientsName = (String) JsonIngredients.get(i).get("food_name");  // Название ингредиента
            System.out.println(IngredientsName);
            double quantityDouble = ((Number) JsonIngredients.get(i).get("quantity")).doubleValue();
            double conversionDouble = ((Number) conversion).doubleValue();

            double result = quantityDouble * conversionDouble;

            double gramsInCupDouble = ((Number) JsonIngredients.get(i).get("grams_in_cup")).doubleValue();
            if (gramsInCupDouble != 0.0 && (measure_int == 7 || measure_int == 8 || measure_int == 9 || measure_int == 10)) {  // Если берём определённые меры, то их ещё умножаем на gramsInCup
                result = gramsInCupDouble / 200 * result;
            }

            for (int j=0; j<table_CPFC.size(); j++) {
                if (Objects.equals(table_CPFC.get(j).get("name").toString().trim(), IngredientsName.trim())) {
                    System.out.println(table_CPFC.get(j));
                    int IngredientsWeight = ((Number) result).intValue();  // Масса каждого ингредиента
                    Ingredient100Menu local_ingredients = new Ingredient100Menu(IngredientsName, IngredientsWeight, Double.parseDouble(table_CPFC.get(j).get("carbohydrates").toString()),  Double.parseDouble(table_CPFC.get(j).get("fats").toString()), Double.parseDouble(table_CPFC.get(j).get("proteins").toString()), Double.parseDouble(table_CPFC.get(j).get("calories").toString()));
                    ingredients_array[i] = new Ingredient100Menu(local_ingredients.name, local_ingredients.quantity, local_ingredients.carbohydrates, local_ingredients.fats, local_ingredients.proteins, local_ingredients.calories);
                }
            }


        }
        this.all_ingredients = ingredients_array;
    }


    public void ParserSteps() {
        Elements steps = Jsoupdocument.select("ol.instructions li");
        RecipeStep100Menu[] steps_array = new RecipeStep100Menu[steps.size()-1];
        int local_index = 0;
        for (int i = 0; i < steps.size(); i++) {
            String[] one_step = new String[2];
            one_step[0] = steps.get(i).select("p.instruction").text();
            one_step[1] = steps.get(i).select("a[href]").attr("href");
            if (!one_step[0].isEmpty() && !one_step[1].isEmpty()) {
                RecipeStep100Menu local_step = new RecipeStep100Menu(one_step[0], one_step[1]);
                steps_array[local_index] = local_step;
                local_index++;
            }
        }
        this.all_steps = steps_array;
    }

    public int time_convert(String cooking_time) {
        int totalMinutes = 0;
        Pattern pattern = Pattern.compile("(\\d+) ?(д|мин|ч)");
        Matcher matcher = pattern.matcher(cooking_time);

        while (matcher.find()) {
            int value = Integer.parseInt(matcher.group(1));
            String unit = matcher.group(2);

            switch (unit) {
                case "д":
                    totalMinutes += value * 24 * 60; // день в минутах
                    break;
                case "ч":
                    totalMinutes += value * 60; // час в минутах
                    break;
                case "мин":
                    totalMinutes += value; // минута
                    break;
            }
        }
        return totalMinutes;
    }

    public Recipe100Menu getRecipeByUrl() {
        ParserIngredients();
        ParserSteps();
        int convert_cooking_time = this.time_convert(cooking_time);
        Recipe100Menu recipe = new Recipe100Menu(name, category, img, convert_cooking_time, all_ingredients, all_steps);
        return recipe;
    }
}