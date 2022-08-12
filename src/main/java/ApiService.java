import org.json.JSONObject;

import java.io.IOException;

public class ApiService {

    public static String discoverDevices(String request){
        JSONObject jsonRequest = new JSONObject(request);
        JSONObject directive = (JSONObject) jsonRequest.get("directive");
        String token = directive.getJSONObject("payload").getJSONObject("scope").optString("token", "INVALID");
        try {
            System.out.println("Making discovery request to remote server....");
            return OkHttpService.post("/v3/discovery", token, request);
        }catch (IOException ex){
            System.out.println("Error fetching discovery response:::"+ex);
            return "{\"error\": \"Invalid token\"}";
        }
    }

    public static String handleRequest(String request){
        JSONObject jsonRequest = new JSONObject(request);
        JSONObject directive = (JSONObject) jsonRequest.get("directive");
        String token = directive.getJSONObject("endpoint").getJSONObject("scope").optString("token", "INVALID");
        try {
            System.out.println("Sending handle request to remote server....");
            return OkHttpService.post("/v3/handleRequest", token, request);
        }catch (IOException ex){
            System.out.println("Error fetching handle request response:::"+ex);
            return "{\"error\": \"Invalid token\"}";
        }
    }
}
