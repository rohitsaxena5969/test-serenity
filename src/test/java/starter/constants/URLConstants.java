package starter.constants;

import java.util.HashMap;
import java.util.Map;

public class URLConstants {
    public static final Map<String, String> customer_url = new HashMap<String, String>() {{
        put("staging", "https://cnollc-cnocdh-stg1.pegacloud.net/prweb/api/TestingAPI/V1/Request?CustomerID=");
        put("boe", "https://cnollc-cnocdh-simulation.pegacloud.net/prweb/api/PegaMKTContainer/V2/CustomerOfferHistory?CustomerID=");
        put("dev", "https://cnollc-cnocdh-dt1.pegacloud.net/prweb/api/TestingAPI/V1/Request?CustomerID=");
    }};

    public static final Map<String, String> ih_url = new HashMap<String, String>() {{
        put("staging", "https://cnollc-cnocdh-stg1.pegacloud.net/prweb/api/PegaMKTContainer/V2/CustomerOfferHistory?CustomerID=");
        put("boe", "https://cnollc-cnocdh-simulation.pegacloud.net/prweb/api/PegaMKTContainer/V2/CustomerOfferHistory?CustomerID=");
        put("dev", "https://cnollc-cnocdh-stg1.pegacloud.net/prweb/api/PegaMKTContainer/V2/CustomerOfferHistory?CustomerID=");
    }};

    public static final Map<String, String> clickstream_url = new HashMap<String, String>() {{
        put("staging", "https://cnollc-cnocdh-stg1.pegacloud.net/prweb/api/Clickstream/1.1/Insert");
        put("boe", "https://cnollc-cnocdh-dt1.pegacloud.net/prweb/api/Clickstream/1.1/Insert");
        put("dev", "https://cnollc-cnocdh-simulation.pegacloud.net/prweb/api/Clickstream/1.1/Insert");
    }};

    public static final Map<String, String> oauth_url = new HashMap<String, String>() {{
        put("staging", "https://cnollc-cnocdh-stg1.pegacloud.net/prweb/PRRestService/oauth2/v1/token");
        put("boe", "https://cnollc-cnocdh-dt1.pegacloud.net/prweb/PRRestService/oauth2/v1/token");
        put("dev", "https://cnollc-cnocdh-simulation.pegacloud.net/prweb/PRRestService/oauth2/v1/token");
    }};

    public static final String jsonBody = "{\n" +
                "\"EventType\": \"EVENT_TYPE\",\n" +
                "\"EventSequenceInSession\": \"516\",\n" +
                "\"VisitorCookieID\": \"9fc57c6e459b48f2abee13eaed7bf010\",\n" +
                "\"VisitorIdentification\": \"CUSTOMER_ID\",\n" +
                "\"VisitorIdentificationStrength\": \"100\",\n" +
                "\"EventTimestamp\": \"DATE.820+00:00\",\n" +
                "\"TransactionName\": \"CPApplication Life Insurance\",\n" +
                "\"SessionKey\": \"SESSION_KEY\",\n" +
                "\"TransactionInstanceID\": \"c855162966154d5b93c42cf47bb8ca10\",\n" +
                "\"ProductName\": \"Permanent Whole Life Insurance\",\n" +
                "\"FirstName\": \"Silviu\",\n" +
                "\"LastName\": \"Stefan\",\n" +
                "\"ProductCode\": \"PRODUCT_ID\",\n" +
                "\"ApplicationC3ID\": \"8a8b40b582ab0df10182ac8bef460114\"\n" +
                "}";
}
