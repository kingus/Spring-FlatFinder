import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scrapper {
    public static Districts districts = Main.districts;

    public static JSONArray getOffersFromGumtree(String district) {
        JSONArray offersJSON = new JSONArray();


        int pageNumber = 1;
        int maxPageNumber = pageNumber;
        do {
            System.out.println("PAGE NUMBER: " + pageNumber);
            System.out.println(district);

            Document doc;
            try {
                System.out.println("https://www.gumtree.pl/s-mieszkania-i-domy-sprzedam-i-kupie/" + district + "/page-" + pageNumber + "/" + districts.getDistricts().get(district) + "p" + (pageNumber));
                doc = Jsoup.connect("https://www.gumtree.pl/s-mieszkania-i-domy-sprzedam-i-kupie/" + district + "/page-" + pageNumber + "/" + districts.getDistricts().get(district) + "p" + (pageNumber)).get();
                if (pageNumber == 1) {
                    Element lastUrlElement = doc.getElementsByClass("icon-double-angle-right").first().parent();
                    String lastUrl = lastUrlElement.attr("href");
                    maxPageNumber = Integer.parseInt(lastUrl.substring(lastUrl.lastIndexOf("p") + 1));
                    System.out.println(lastUrl);
                }

                Elements offers = doc.getElementsByClass("tileV1");

                for (Element offer : offers) {
                    try {
                        JSONObject offerDataJSON = new JSONObject();

                        offerDataJSON.put("source", "Gumtree");
                        offerDataJSON.put("district", district);

                        String offerDocURL = offer.getElementsByClass("href-link tile-title-text").attr("href");
                        offerDocURL = "https://www.gumtree.pl" + offerDocURL;

                        Document offerDoc = Jsoup.connect(offerDocURL).get();

                        String oOfferUrl = offerDocURL;
                        System.out.println(oOfferUrl);
                        offerDataJSON.put("offer_url", oOfferUrl);


                        Element oOfferImgElement = offer.select("source[type*=image/jpeg]").first();
                        String oOfferImg = oOfferImgElement.attr("data-srcset");
                        System.out.println(oOfferImg);
                        offerDataJSON.put("img_url", oOfferImg);


                        String oTitle = offerDoc.getElementsByClass("myAdTitle").text();
                        System.out.println(oTitle);
                        offerDataJSON.put("title", oTitle);


                        String oSourceId = offerDoc.select("body > div.viewport > div.containment > div > div.breadcrumbs > span.title").text().substring(11);
                        System.out.println(oSourceId);
                        offerDataJSON.put("source_id", oSourceId);


                        Element oAreaElement = offerDoc.select("span:contains(Wielkość (m2))").first().siblingElements().first();
                        String oArea = oAreaElement.text();
                        System.out.println(oArea);
                        offerDataJSON.put("area", oArea);


                        Element oRoomsElement = offerDoc.select("span:contains(Liczba pokoi)").first().siblingElements().first();
                        String oRooms = oRoomsElement.text();
                        offerDataJSON.put("rooms", oRooms);


                        String oPrice = offerDoc.select("#wrapper > div:nth-child(1) > div.vip-header-and-details > div.vip-content-header > div.vip-title.clearfix > div > span > span").text();
                        oPrice = oPrice.replaceAll("[^\\d]", "");
                        System.out.println(oPrice);
                        offerDataJSON.put("price", oPrice);


                        Element geoCoord = offerDoc.select("script[type*=application/ld+json]").first();
                        try {
                            JSONArray jsonArray = (JSONArray) new JSONParser().parse(geoCoord.data());
                            JSONObject jsonObjectFromArray = (JSONObject) jsonArray.get(1);
                            JSONObject jsonGeoData = (JSONObject) new JSONParser().parse(jsonObjectFromArray.get("geo").toString());

                            offerDataJSON.put("latitude", jsonGeoData.get("latitude"));
                            offerDataJSON.put("longitude", jsonGeoData.get("longitude"));

                        } catch (Exception exception) {
                            System.out.println("Parsing coordinates exception: " + exception.getMessage());
                        }
                        offersJSON.add(offerDataJSON);
                        System.out.println(offerDataJSON);
                        System.out.println(offersJSON.size());
                    } catch (Exception exception) {
                        System.out.println("Offer exception: " + exception.getMessage() + ". Wrong data, offer has been skipped");
                    }
                }
            } catch (Exception exception) {
                System.out.println("Page exception: " + exception.getMessage() + ". Wrong data, page has been skipped");
                continue;
            }
            pageNumber++;
        } while (pageNumber <= maxPageNumber);


        System.out.println("Returning data...");
        return offersJSON;
    }
}
