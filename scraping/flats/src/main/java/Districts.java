import java.util.HashMap;
import java.util.Map;

public class Districts {
    private final Map<String, String> districts;

    Districts() {
        districts = new HashMap<>();
        for (District dis : District.values()) {
            districts.put(dis.name(), dis.district);
        }
    }

    public Map<String, String> getDistricts() {
        return districts;
    }
}
