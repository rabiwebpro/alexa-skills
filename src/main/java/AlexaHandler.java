import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Scanner;

import com.amazonaws.services.lambda.runtime.Context;
import org.json.*;

public class AlexaHandler {

    public static void handler(InputStream inputStream, OutputStream outputStream, Context context) {

        String request;
        try {
            request = getRequest(inputStream);
            System.out.println("Request:");
            System.out.println(request);

            /*ObjectMapper mapper = new ObjectMapper();
            Directive directive = mapper.readValue(request, Directive.class);
            Header header = directive.getHeader();*/

            JSONObject jsonRequest = new JSONObject(request);
            JSONObject directive = (JSONObject) jsonRequest.get("directive");
            JSONObject header = (JSONObject) directive.get("header");

            AlexaResponse ar = null;
            String apiResponse = null;

            String namespace = header.optString("namespace", "INVALID");
            String correlationToken = header.optString("correlationToken", "INVALID");
            switch(namespace) {

                case "Alexa.Authorization":
                    apiResponse = ApiService.handleAuthorization(request);
                    //System.out.println("Found Alexa.Authorization Namespace");
                    //ar = new AlexaResponse("Alexa.Authorization","AcceptGrant", "INVALID", "INVALID", correlationToken);
                    break;

                case "Alexa.Discovery":
                    apiResponse = ApiService.discoverDevices(request);
                    //discoverResponse = "{\"event\":{\"endpoint\":{\"endpointId\":\"INVALID\",\"scope\":{\"type\":\"BearerToken\",\"token\":\"INVALID\"}},\"header\":{\"namespace\":\"Alexa.Discovery\",\"name\":\"Discover.Response\",\"messageId\":\"2dd45026-8434-4aad-b720-2feda1023489\",\"payloadVersion\":\"3\"},\"payload\":{\"endpoints\":[{\"endpointId\":\"ddd1bExva1BhPeUd4\",\"manufacturerName\":\"CircuitIoT\",\"description\":\"Sample description\",\"friendlyName\":\"Living Room Group 1\",\"displayCategories\":[\"SWITCH\"],\"additionalAttributes\":{\"manufacturer\":\"CircuitIoT\",\"model\":\"Switch 4 Gang\"},\"capabilities\":[{\"type\":\"AlexaInterface\",\"version\":\"3\",\"interface\":\"Alexa\"},{\"type\":\"AlexaInterface\",\"version\":\"3\",\"properties\":{\"supported\":[{\"name\":\"connectivity\"}],\"proactivelyReported\":true,\"retrievable\":true},\"interface\":\"Alexa.EndpointHealth\"},{\"type\":\"AlexaInterface\",\"version\":\"3\",\"properties\":{\"supported\":[{\"name\":\"powerState\"}],\"proactivelyReported\":true,\"retrievable\":true},\"interface\":\"Alexa.PowerController\"}]},{\"endpointId\":\"dddfIhMJpGDko8nUW\",\"manufacturerName\":\"CircuitIoT\",\"description\":\"Sample description 2\",\"friendlyName\":\"Living Room Group 1 Switch 1\",\"displayCategories\":[\"SWITCH\"],\"additionalAttributes\":{\"manufacturer\":\"CircuitIoT\",\"model\":\"Switch 4 Gang\"},\"capabilities\":[{\"type\":\"AlexaInterface\",\"version\":\"3\",\"interface\":\"Alexa\"},{\"type\":\"AlexaInterface\",\"version\":\"3\",\"properties\":{\"supported\":[{\"name\":\"connectivity\"}],\"proactivelyReported\":true,\"retrievable\":true},\"interface\":\"Alexa.EndpointHealth\"},{\"type\":\"AlexaInterface\",\"version\":\"3\",\"properties\":{\"supported\":[{\"name\":\"powerState\"}],\"proactivelyReported\":true,\"retrievable\":true},\"interface\":\"Alexa.PowerController\"}]}]}}}";

                    //System.out.println("Discovery Response::::::"+discoverResponse);
                    /*String discoveryToken = directive.getJSONObject("payload").getJSONObject("scope").optString("token", "INVALID");
                    System.out.println("Found Alexa.Discovery Namespace");
                    System.out.println("With token: "+discoveryToken);
                    ar = new AlexaResponse("Alexa.Discovery", "Discover.Response");
                    String capabilityAlexa = ar.CreatePayloadEndpointCapability("AlexaInterface", "Alexa", "3", null);
                    String capabilityAlexaPowerController = ar.CreatePayloadEndpointCapability("AlexaInterface", "Alexa.PowerController", "3", "{\"supported\": [ { \"name\": \"powerState\" } ] }");
                    String capabilities = "[" + capabilityAlexa + ", " + capabilityAlexaPowerController + "]";
                    ar.AddPayloadEndpoint("Sample Switch", "sample-switch-01", capabilities);
                    System.out.println("Custom response:::::"+ar.toString());*/

                    // For another way to see how to craft an AlexaResponse, have a look at AlexaResponseTest:ResponseDiscovery

                    break;

                case "Alexa.PowerController":
                    System.out.println("Found Alexa.PowerController Namespace");
                    apiResponse = ApiService.handleRequest(request);
                    /*String endpointId = directive.getJSONObject("endpoint").optString("endpointId", "INVALID");
                    String token = directive.getJSONObject("endpoint").getJSONObject("scope").optString("token", "INVALID");
                    String powerStateValue = directive.getJSONObject("header").optString("name", "TurnOn");
                    String value = powerStateValue.equals("TurnOn") ? "ON" : "OFF";

                    // Set the value in the DynamodDB table SampleSmartHome
                    if(sendDeviceState(endpointId, "powerState", value)) {
                        ar = new AlexaResponse("Alexa", "Response", endpointId, token, correlationToken);
                        ar.AddContextProperty("Alexa.PowerController", "powerState", value, 200);
                    }
                    else {
                        ar = new AlexaResponse("Alexa", "ErrorResponse");
                    }*/

                    break;
                case "Alexa":
                    System.out.println("Found Alexa Namespace");
                    apiResponse = ApiService.handleRequest(request);
                    break;

                default:
                    System.out.println("INVALID Namespace");
                    ar = new AlexaResponse();
                    break;
            }

            System.out.println("Response:");
            if(apiResponse != null){
                System.out.println(apiResponse);
                outputStream.write(apiResponse.getBytes(Charset.forName("UTF-8")));
            }else{
                System.out.println(ar);
                outputStream.write(ar.toString().getBytes(Charset.forName("UTF-8")));
            }



        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    static boolean sendDeviceState(String endpoint_id, String state, String value) {
        return true;
    }

    static String getRequest(java.io.InputStream is) {
        Scanner s = new Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
