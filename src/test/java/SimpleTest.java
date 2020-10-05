import com.google.gson.*;
import com.jayway.restassured.response.ValidatableResponse;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONValue;

// https://semaphoreci.com/community/tutorials/testing-rest-endpoints-using-rest-assured
// com.jayway.restassured
public class SimpleTest {

    Gson gson = new Gson();

    @Test
    public void testToJson() {
        Map<Integer, String> colours = new HashMap<>();
        colours.put(1, "blue");
        colours.put(2, "yellow");
        colours.put(3, "green");

        Gson gson = new Gson();
        String output = gson.toJson(colours);
        System.out.println(output);
    }

    @Test
    public void testSipleJson() {
        String jsonString =
                "{\"name\":\"Mahesh Kumar\", \"age\":21,\"verified\":false,\"marks\": [100,90,85]}";
        JsonParser parser = new JsonParser();
        JsonElement rootNode = parser.parse(jsonString);

        if (rootNode.isJsonObject()) {
            JsonObject details = rootNode.getAsJsonObject();

            JsonElement nameNode = details.get("name");
            System.out.println("Name: " +nameNode.getAsString());

            JsonElement ageNode = details.get("age");
            System.out.println("Age: " + ageNode.getAsInt());

            JsonElement verifiedNode = details.get("verified");
            System.out.println("Verified: " + (verifiedNode.getAsBoolean() ? "Yes":"No"));

            JsonArray marks = details.getAsJsonArray("marks");
            for (int i = 0; i < marks.size(); i++) {
                JsonPrimitive value = marks.get(i).getAsJsonPrimitive();
                System.out.print(value.getAsInt() + " ");
            }
        }
    }

    @Test
    public void testSearchRequest() throws Exception {
        ValidatableResponse responce = given()
                .param("part", "Моск")
                .get(EndPoints.avia, "airports")
                .then()
                .body("success", equalTo(true))
                .body("data.list[0].airport_aggregation_iata", equalTo("MOW"))
                .statusCode(200);
        System.out.println(responce.extract().body().asString());
        String pid = responce.extract().path("pid");
        System.out.println(pid);
        System.out.println("--------------------------------------------------------");
        List<Object> data_list = responce.extract().path("data.list");
        // System.out.println(data_list);
        for (int i=0; i < data_list.size(); i++)
        {
            // System.out.println(data_list.get(i));
        }
        System.out.println(data_list.get(0));
        System.out.println("--------------------------------------------------------");
        JsonParser parser = new JsonParser();
        JsonElement rootNode = parser.parse(responce.extract().body().asString());

        if (rootNode.isJsonObject()) {
            JsonObject details = rootNode.getAsJsonObject();
            JsonElement data_element = details.get("data");
            System.out.println(data_element);

            // JsonElement nameNode = details.get("success");
            // System.out.println("success: " +nameNode.getAsString());
        }
    }

    public static void getResponseStatusCode() {
        given()
                .param("part", "Моск")
                .get("http://api4pre.aviakassa.ru/v4/avia/airports")
                .then()
                .body("success", equalTo(true))
                .body("data.list[0].airport_aggregation_iata", equalTo("MOW"))
                .statusCode(200);
    }
}