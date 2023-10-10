package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import helpers.PropertyProvider;
import io.restassured.http.ContentType;
import models.AdditionObj;
import models.EntitiesResponse;
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
    final String URL = PropertyProvider.getInstance().getProperties("url");
    ObjectMapper objectMapper = new ObjectMapper();

    public String createEntityStep() {
        EntityRequest body = new EntityRequest();
        body.setAddition(new EntityRequest.Addition("Дополнительные сведения", 123));
        body.setImportant_numbers(Arrays.asList(42, 87, 15));
        body.setTitle("Заголовок сущности");
        body.setVerified(true);

        return createEntityStep(body);
    }

    public String createEntityStep(EntityRequest body) {
        String response = given()
                .baseUri(URL)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/api/create")
                .then()
                .statusCode(200)
                .extract()
                .asString();
        return response;
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

    public int deleteEntityStep(int entityId) {
        return given()
                .baseUri(URL)
                .pathParam("id", entityId)
                .when()
                .delete("/api/delete/{id}")
                .then()
                .extract()
                .statusCode();
    }

    public int patchEntityStep(int entityId, EntityRequest body) {
        return given()
                .baseUri(URL)
                .pathParam("id", entityId)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .patch("/api/patch/{id}")
                .then()
                .extract()
                .statusCode();
    }

    public EntitiesResponse getListOfEntitiesStep(String queryParamName, String queryParamValue) throws JsonProcessingException {
        String response = given()
                .baseUri(URL)
                .queryParam(queryParamName, queryParamValue)
                .when()
                .get("/api/getAll")
                .then()
                .statusCode(200)
                .extract()
                .asString();

        return objectMapper.readValue(response, EntitiesResponse.class);
    }

}

