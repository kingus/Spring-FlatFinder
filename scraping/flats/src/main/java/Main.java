import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Main {
    private static final String apiUrl = "http://localhost:8080/api/offers";
    public static HttpURLConnection connection;
    public static Districts districts;

    public static void main(String[] args) {
        districts = new Districts();
        getAllOffersFromGumtree();
    }

    public static void configureConnection() throws IOException {
        URL url = new URL(apiUrl);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(RequestMethod.METHOD_POST);
        connection.setRequestProperty(RequestProperty.CONTENT_TYPE, RequestProperty.APPLICATION_JSON);
        connection.setRequestProperty(RequestProperty.ACCEPT, RequestProperty.APP_JSON_UTF_8);
        connection.setDoOutput(true);
    }

    public static void saveDataToDatabase(JSONArray offers) throws IOException {
        for (Object offer : offers) {
            JSONObject obj = (JSONObject) offer;
            configureConnection();

            try {
                String jsonInputString = obj.toJSONString();

                try (OutputStream os = connection.getOutputStream()) {

                    byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                    os.write(input);

                } catch (Exception nestedException) {
                    nestedException.printStackTrace();
                }

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    in.close();
                }

            } catch (Exception exception) {
                System.out.println("Exception POST Request: " + exception.getMessage());
            }
        }
    }

    public static void getAllOffersFromGumtree() {

        for (final String district : districts.getDistricts().keySet()) {

            Thread thread = new Thread() {
                public void run() {
                    System.out.println(district + " thread running");
                    try {
                        saveDataToDatabase(Scrapper.getOffersFromGumtree(district));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
        }

    }
}