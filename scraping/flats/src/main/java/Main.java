import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Connection;

public class Main {
    public static HttpURLConnection connection;

    public static void configureConnection() throws IOException {
        URL url = new URL("http://localhost:8080/api/offers");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);
    }

    public static void saveDataToDatabase(JSONArray offers) throws IOException {
        for (int i = 0 ; i < offers.size(); i++) {
            JSONObject obj = (JSONObject) offers.get(i);
            configureConnection();
            try {
                String jsonInputString = obj.toJSONString();

                try(OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                } catch (Exception nestedException) {
                    System.out.println("Setting output exception: " + nestedException.getMessage());
                }

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    in.close();
                } else {
                }
            } catch (Exception exception) {
                System.out.println("Exception POST Request: " + exception.getMessage());
            }
        }
    }

    public static void getAllOffersFromGumtree() {
        for (final String district : Scrapper.districts.keySet()){
            Thread thread = new Thread(){
                public void run(){
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

    public static void main(String[] args) {
        getAllOffersFromGumtree();
    }

}