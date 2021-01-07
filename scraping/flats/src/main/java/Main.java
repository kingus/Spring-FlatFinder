import org.json.simple.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void main(String[] args) {

        JSONArray offersFromGumtree = Scrapper.getOffersFromGumtree("wola");
        System.out.println("Offers amount: " + offersFromGumtree.size());
        System.out.println(offersFromGumtree.toJSONString());
    }
}