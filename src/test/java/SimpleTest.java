import com.google.gson.Gson;
import com.google.gson.JsonElement;
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
        List<Object> data_list = responce.extract().path("data.list");
        // System.out.println(data_list);
        for (int i=0; i < data_list.size(); i++)
        {
            // System.out.println(data_list.get(i));
        }
        System.out.println(data_list.get(0));
    }

    public static void getResponseStatusCode(){
        given()
                .param("part", "Моск")
                .get("http://api4pre.aviakassa.ru/v4/avia/airports")
                .then()
                .body("success", equalTo(true))
                .body("data.list[0].airport_aggregation_iata", equalTo("MOW"))
                .statusCode(200);
    }
}