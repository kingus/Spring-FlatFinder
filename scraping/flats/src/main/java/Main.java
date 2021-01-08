import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
    public static void main(String[] args) {
        Document doc;
        try {
            doc = Jsoup.connect("https://www.otodom.pl/sprzedaz/mieszkanie/warszawa/wola/").get();
//            Element content = doc.getElementById("content");
            Elements offers = doc.getElementsByClass("offer-item-details");
            System.out.println(offers);
            for (Element offer : offers) {
                String title = offer.getElementsByClass("offer-item-title").text();
                String place = offer.getElementsByClass("text-nowrap").last().ownText();
                String area = offer.getElementsByClass("hidden-xs offer-item-area").select("li").text();
                String pricePerM = offer.getElementsByClass("hidden-xs offer-item-price-per-m").select("li").text();
                String rooms = offer.getElementsByClass("offer-item-rooms hidden-xs").select("li").text();
                String url = offer.select("a").attr("href");
                System.out.println("TITLE " + title);
                System.out.println("PLACE " + place);
                System.out.println("AREA " + area);
                System.out.println("pricePerM " + pricePerM);
                System.out.println("rooms " + rooms);
                System.out.println("url " + url);
            }
        }
        catch (Exception exception){

        }
}
    }