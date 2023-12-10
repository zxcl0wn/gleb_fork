package Parser100menu;

import com.google.gson.annotations.JsonAdapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class AllCPFC {
    public Vector<Map> table_CPFC() throws IOException {
        Vector<String> all_tables = new Vector<>();
        Document all_tables_doc = Jsoup.connect("https://1000.menu/food-table").get();
        Elements all_tables_elements = all_tables_doc.select("a.as-tag");
        for (int i=0; i<all_tables_elements.size(); i++) {
            String one_element_table = all_tables_elements.get(i).attr("href");
            all_tables.add("https://1000.menu" + one_element_table);
        }

//        List<String> all_tables = List.of(
//                "https://1000.menu/calories/yaitsa",
//                "https://1000.menu/calories/myaso-i-myasoproduktj",
//                "https://1000.menu/calories/polufabrikatj",
//                "https://1000.menu/calories/rjba-i-moreproduktj",
//                "https://1000.menu/calories/moreproduktj",
//                "https://1000.menu/calories/sjr",
//                "https://1000.menu/calories/molochnje-produktj",
//                "https://1000.menu/calories/gribj",
//                "https://1000.menu/calories/ovoshchi",
//                "https://1000.menu/calories/muka",
//                "https://1000.menu/calories/fruktj-yagodj",
//                "https://1000.menu/calories/iz-suxofruktov",
//                "https://1000.menu/calories/jagodi",
//                "https://1000.menu/calories/orexi",
//                "https://1000.menu/calories/sladkoe",
//                "https://1000.menu/calories/krupa-i-krupyanje-izdeliya",
//                "https://1000.menu/calories/makaronnje-izdeliya",
//                "https://1000.menu/calories/bobovje",
//                "https://1000.menu/calories/xlebobulochnje-izdeliya",
//                "https://1000.menu/calories/spetsii-pripravj-pryanosti",
//                "https://1000.menu/calories/listya-tsvetki-korenya",
//                "https://1000.menu/calories/zhirj-i-masla",
//                "https://1000.menu/calories/alkogol",
//                "https://1000.menu/calories/drugoe"
//        );

        Vector<Map> CPFC = new Vector<>();

//    Цикл по всем ссылкам
        for (int i=0; i<all_tables.size(); i++) {
            Document document = Jsoup.connect(all_tables.get(i)).timeout(1000000).get();
            Elements table = document.select("table#food-table tbody tr");

//        Цикл по каждому ингредиенту
            for (int j=0; j<table.size(); j++) {
                Element element = table.get(j);
                Elements tdElements = element.select("td");
                if (tdElements.isEmpty()) {
                    continue;
                }

                Map<String, String> one_ingridient = new HashMap<>();
//            Цикл по КБЖУ одного ингредиента
                for (int y = 0; y < tdElements.size(); y++) {
                    Element tdElement = tdElements.get(y);
                    String tdText = tdElement.text();
                    switch (y) {
                        case 0:
                            one_ingridient.put("name", tdText);
                            continue;
                        case 1:
                            one_ingridient.put("proteins", tdText);
                            continue;
                        case 2:
                            one_ingridient.put("fats", tdText);
                            continue;
                        case 3:
                            one_ingridient.put("carbohydrates", tdText);
                            continue;
                        case 4:
                            one_ingridient.put("calories", tdText);
                    }
                }
                CPFC.add(one_ingridient);
            }
        }

        return CPFC;
    }
}
