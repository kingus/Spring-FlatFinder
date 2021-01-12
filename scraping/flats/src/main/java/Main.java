import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.sun.tools.internal.ws.processor.model.Request;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {
    public static void saveDataToDatabase(JSONArray offers) {
        for (int i = 0 ; i < offers.size(); i++) {
            JSONObject obj = (JSONObject) offers.get(i);


            try {
                URL url = new URL("http://localhost:8080/api/offers");
                String readLine = null;
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; utf-8");
                connection.setRequestProperty("Accept", "application/json");
                connection.setDoOutput(true);
                String jsonInputString = obj.toJSONString();
                try(OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                } catch (Exception exceptionNested) {
                    System.out.println("EN: " + exceptionNested.getMessage());
                }
                //connection.setRequestMethod();
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    StringBuffer response = new StringBuffer();
                    while ((readLine = in.readLine()) != null) {
                        response.append(readLine);
                    }
                    in.close();
//                    System.out.println("JSON string result: " + response.toString());
                } else {
                    System.out.println("POST NOT WORKED");
                }
            } catch (Exception exception) {
                System.out.println("Exception POST Request: " + exception.getMessage());
            }


        }
    }

    public static void getAllOffersFromGumtree() {
        saveDataToDatabase(Scrapper.getOffersFromGumtree("bemowo"));
        saveDataToDatabase(Scrapper.getOffersFromGumtree("bialoleka"));
        saveDataToDatabase(Scrapper.getOffersFromGumtree("bielany"));
        saveDataToDatabase(Scrapper.getOffersFromGumtree("mokotow"));
        saveDataToDatabase(Scrapper.getOffersFromGumtree("ochota"));
        saveDataToDatabase(Scrapper.getOffersFromGumtree("praga-poludnie"));
        saveDataToDatabase(Scrapper.getOffersFromGumtree("praga-polnoc"));
        saveDataToDatabase(Scrapper.getOffersFromGumtree("rembertow"));
        saveDataToDatabase(Scrapper.getOffersFromGumtree("targowek"));
        saveDataToDatabase(Scrapper.getOffersFromGumtree("ursus"));
        saveDataToDatabase(Scrapper.getOffersFromGumtree("ursynow"));
        saveDataToDatabase(Scrapper.getOffersFromGumtree("wawer"));
        saveDataToDatabase(Scrapper.getOffersFromGumtree("wesola"));
        saveDataToDatabase(Scrapper.getOffersFromGumtree("wilanow"));
        saveDataToDatabase(Scrapper.getOffersFromGumtree("wola"));
        saveDataToDatabase(Scrapper.getOffersFromGumtree("wlochy"));
        saveDataToDatabase(Scrapper.getOffersFromGumtree("srodmiescie"));
        saveDataToDatabase(Scrapper.getOffersFromGumtree("zoliborz"));
    }
    public static void main(String[] args) {

        saveDataToDatabase(Scrapper.getOffersFromGumtree("rembertow"));
        saveDataToDatabase(Scrapper.getOffersFromGumtree("wesola"));
        //malo ofert w tych dzielnicach, dobre do testów

        // Czas wykonywania - kilkanascie minut
        //getAllOffersFromGumtree();






    }
}