package steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Step;
import models.EntitiesResponse;
import models.EntityRequest;
import models.EntityResponse;

import java.util.Arrays;

import static io.restassured.RestAssured.given;

public class Steps {
    ObjectMapper objectMapper = new ObjectMapper();

    public String createEntityStep() {
        EntityRequest body = new EntityRequest();
        body.setAddition(new EntityRequest.Addition("Дополнительные сведения", 123));
        body.setImportant_numbers(Arrays.asList(42, 87, 15));
        body.setTitle("Заголовок сущности");
        body.setVerified(true);

        return createEntityStep(body);
    }

    @Step("Созать сущность")
    public String createEntityStep(EntityRequest body) {
        return given()
                .body(body)
                .when()
                .post("/api/create")
                .then()
                .statusCode(200)
                .extract()
                .asString();
    }

    @Step("Получить сущность")
    public EntityResponse getEntityResponseStep(int id) throws JsonProcessingException {
        String response = given()
                .pathParam("id", id)
                .when()
                .get("/api/get/{id}")
                .then()
                .extract()
                .asString();

        return objectMapper.readValue(response, EntityResponse.class);
    }

    @Step("Удалить сущность")
    public int deleteEntityStep(int entityId) {
        return given()
                .pathParam("id", entityId)
                .when()
                .delete("/api/delete/{id}")
                .then()
                .extract()
                .statusCode();
    }

    @Step("Обновить сущность")
    public int patchEntityStep(int entityId, EntityRequest body) {
        return given()
                .pathParam("id", entityId)
                .body(body)
                .when()
                .patch("/api/patch/{id}")
                .then()
                .extract()
                .statusCode();
    }

    @Step("Получить список сущностей")
    public EntitiesResponse getListOfEntitiesStep(String queryParamName, String queryParamValue) throws JsonProcessingException {
        String response = given()
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

