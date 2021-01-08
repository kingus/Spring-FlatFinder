import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import sun.rmi.runtime.Log;

import java.util.HashMap;
import java.util.Map;

public class Scrapper {
    static Map<String, String> districts;

    static {
        districts = new HashMap<String, String>();
        districts.put("bemowo", "v1c9073l3200009");
        districts.put("bialoleka", "v1c9073l3200010");
        districts.put("bielany", "v1c9073l3200011");
        districts.put("mokotow", "v1c9073l3200012");
        districts.put("ochota", "v1c9073l3200013");
        districts.put("praga-poludnie", "v1c9073l3200015");
        districts.put("praga-polnoc", "v1c9073l3200014");
        districts.put("rembertow", "v1c9073l3200016");
        districts.put("targowek", "v1c9073l3200018");
        districts.put("ursus", "v1c9073l3200019");
        districts.put("ursynow", "v1c9073l3200020");
        districts.put("wawer", "v1c9073l3200021");
        districts.put("wesola", "v1c9073l3200022");
        districts.put("wilanow", "v1c9073l3200023");
        districts.put("wola", "v1c9073l3200025");
        districts.put("wlochy", "v1c9073l3200024");
        districts.put("srodmiescie", "v1c9073l3200017");
        districts.put("zoliborz", "v1c9073l3200026");

    }

    public static JSONArray getOffersFromGumtree(String district) {
        JSONArray offersJSON = new JSONArray();

        int pageNumber = 1;
        int maxPageNumber = pageNumber;
        do {
            System.out.println("PAGE NUMBER: " + pageNumber);
            System.out.println(district);
            System.out.println(districts.get(district));
            Document doc;
            try {
                System.out.println("https://www.gumtree.pl/s-mieszkania-i-domy-sprzedam-i-kupie/" + district + "/page-" + Integer.toString(pageNumber) + "/" + districts.get(district) + "p" + Integer.toString(pageNumber));
                doc = Jsoup.connect("https://www.gumtree.pl/s-mieszkania-i-domy-sprzedam-i-kupie/" + district + "/page-" + Integer.toString(pageNumber) + "/" + districts.get(district) + "p" + Integer.toString(pageNumber)).get();
                if (pageNumber == 1) {
                    Element lastUrlElement = doc.getElementsByClass("icon-double-angle-right").first().parent();
                    String lastUrl = lastUrlElement.attr("href");
                    maxPageNumber = Integer.parseInt(lastUrl.substring(lastUrl.lastIndexOf("p")+1));
                    System.out.println(lastUrl);
                }

                Elements offers = doc.getElementsByClass("tileV1");

                for (Element offer : offers) {
                    try {
                        JSONObject offerDataJSON = new JSONObject();

                        offerDataJSON.put("source", "Gumtree");
                        offerDataJSON.put("district", district);

                        //  GETTING SPECYFIC OFFER PAGE
                        String offerDocURL = offer.getElementsByClass("href-link tile-title-text").attr("href");
                        offerDocURL = "https://www.gumtree.pl" +offerDocURL;

                        Document offerDoc = Jsoup.connect(offerDocURL).get();

                        //  SCRAPPING OFFER URL
                        String oOfferUrl = offerDocURL;
                        System.out.println(oOfferUrl);
                        offerDataJSON.put("offer_url", oOfferUrl);

                        // //  SCRAPPING OFFER LOW RESOLUTION IMAGE URL
                        Element oOfferImgElement = offer.select("source[type*=image/jpeg]").first();
                        String oOfferImg = oOfferImgElement.attr("data-srcset");
                        System.out.println(oOfferImg);
                        offerDataJSON.put("img_url", oOfferImg);


                        //  SCRAPPING TITLE
                        String oTitle = offerDoc.getElementsByClass("myAdTitle").text();
                        System.out.println(oTitle);
                        offerDataJSON.put("title", oTitle);


                        //  SCRAPPING SOURCE ID
                        String oSourceId = offerDoc.select("body > div.viewport > div.containment > div > div.breadcrumbs > span.title").text().substring(11);
                        System.out.println(oSourceId);
                        offerDataJSON.put("source_id", oSourceId);

                        //  SCRAPPING AREA
                        Element oAreaElement =  offerDoc.select("span:contains(Wielkość (m2))").first().siblingElements().first();
                        String oArea = oAreaElement.text();
                        System.out.println(oArea);
                        offerDataJSON.put("area", oArea);

                        //  SCRAPPING ROOMS
                        Element oRoomsElement =  offerDoc.select("span:contains(Liczba pokoi)").first().siblingElements().first();
                        String oRooms = oRoomsElement.text();
                        offerDataJSON.put("rooms", oRooms);

                        //  SCRAPPING PRICE
                        String oPrice = offerDoc.select("#wrapper > div:nth-child(1) > div.vip-header-and-details > div.vip-content-header > div.vip-title.clearfix > div > span > span").text();
                        oPrice = oPrice.replaceAll("[^\\d]", "");
                        System.out.println(oPrice);
                        offerDataJSON.put("price", oPrice);

                        //  SCRAPPING GEO COORDINATES
                        Element geoCoord = offerDoc.select("script[type*=application/ld+json]").first();
                        try {
                            JSONArray jsonArray = (JSONArray) new JSONParser().parse(geoCoord.data());
                            JSONObject jsonObjectFromArray = (JSONObject) jsonArray.get(1);
                            JSONObject jsonGeoData = (JSONObject) new JSONParser().parse(jsonObjectFromArray.get("geo").toString());

                            offerDataJSON.put("latitude", jsonGeoData.get("latitude"));
                            offerDataJSON.put("longitude", jsonGeoData.get("longitude"));

                        }catch (Exception exception){
                            System.out.println("Parsing coordinates exception: " + exception.getMessage());
                        }
                        offersJSON.add(offerDataJSON);
                        System.out.println(offerDataJSON);
                        System.out.println(offersJSON.size());
                    } catch (Exception exception) {
                            System.out.println("Offer exception: " + exception.getMessage() + ". Wrong data, offer has been skipped");
                            continue;
                    }
                }
            }
            catch (Exception exception){
                System.out.println("Page exception: " + exception.getMessage() + ". Wrong data, page has been skipped");
                continue;
            }
            pageNumber++;
        } while (pageNumber <= maxPageNumber);


        System.out.println("Returning data...");
        return offersJSON;
    }
    public static JSONObject getOffersFromOtodom(String district) {
        /*Document doc;
        try {
            doc = Jsoup.connect("https://www.otodom.pl/sprzedaz/mieszkanie/warszawa/wola/").get();
//            Element content = doc.getElementById("content");
            Elements offers = doc.getElementsByClass("offer-item-details");
            System.out.println(offers);
            for (Element offer : offers) {
                String oTitle = offer.getElementsByClass("offer-item-title").text();
                String oPlace = offer.getElementsByClass("text-nowrap").last().ownText();
                String oArea = offer.getElementsByClass("hidden-xs offer-item-area").select("li").text();
                String oPricePerM = offer.getElementsByClass("hidden-xs offer-item-price-per-m").select("li").text();
                String oRooms = offer.getElementsByClass("offer-item-rooms hidden-xs").select("li").text();
                String oUrl = offer.select("a").attr("href");
/*  Strzał po współrzędne
                URL url = new URL("https://maps.googleapis.com/maps/api/geocode/json?address=Warszawa,Wola&key=API_KEY");
                String readLine = null;
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
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
                    System.out.println("JSON string result: " + response.toString());
                } else {
                    System.out.println("GET NOT WORKED");
                }

                System.out.println("TITLE " + oTitle);
                System.out.println("PLACE " + oPlace);
                System.out.println("AREA " + oArea);
                System.out.println("pricePerM " + oPricePerM);
                System.out.println("rooms " + oRooms);
                System.out.println("url " + oUrl);
            }
        }
        catch (Exception exception){

        }
        */
        return new JSONObject();
    }
    public static void main(String[] args) {

    }
}
