package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import helpers.PropertyProvider;
import io.restassured.http.ContentType;
import models.AdditionObj;
import models.EntityRequest;
import models.EntityResponse;
import org.junit.jupiter.api.parallel.ResourceLock;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.parallel.ResourceAccessMode.READ;
import static org.junit.jupiter.api.parallel.Resources.GLOBAL;
import static org.junit.jupiter.api.parallel.Resources.SYSTEM_PROPERTIES;

public class Steps {
    ObjectMapper objectMapper = new ObjectMapper();

    public int createEntityStep() throws IOException, InterruptedException {
        EntityRequest body = new EntityRequest();
        body.setAddition(new AdditionObj("Дополнительные сведения", 123));
        body.setImportant_numbers(Arrays.asList(42, 87, 15));
        body.setTitle("Заголовок сущности");
        body.setVerified(true);

        return createEntityStep(body);
    }

    @ResourceLock(value = GLOBAL, mode = READ)
    public int createEntityStep(EntityRequest body) throws IOException, InterruptedException {
//        String response = given()
//                .baseUri(PropertyProvider.getInstance().getProperties("url"))
//                .contentType(ContentType.JSON)
//                .body(body)
//            .when()
//                .post("/api/create")
//            .then()
//                .extract()
//                .asString();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(PropertyProvider.getInstance().getProperties("url") + "/api/create"))
                .header("Content-Type", "application/json; charset=UTF-8")
                .header("accept", "text/plain")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(body)))
                .build();
        var res = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        System.out.println("res = " + res);
        return Integer.parseInt(res);
    }

    public EntityResponse getEntityResponseStep(int id) throws JsonProcessingException {
        String response = given()
                .baseUri(PropertyProvider.getInstance().getProperties("url"))
                .pathParam("id", id)
            .when()
                .get("/api/get/{id}")
            .then()
                .extract()
                .asString();

        return objectMapper.readValue(response, EntityResponse.class);
    }
}
