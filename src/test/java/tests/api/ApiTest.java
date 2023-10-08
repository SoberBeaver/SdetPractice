package tests.api;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.http.ContentType;
import models.AdditionObj;
import models.EntitiesResponse;
import models.EntityRequest;
import models.EntityResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.junit.jupiter.api.Assertions.*;

public class ApiTest extends BaseTest {
    @Test
    @Epic("Управление сущностями")
    @Feature("Создание сущности")
    @Description("Проверка метода создания сущности")
    public void createEntityTest() {
        EntityRequest body = new EntityRequest();
        body.setAddition(new AdditionObj("Дополнительные сведения", 123));
        body.setImportant_numbers(Arrays.asList(42, 87, 15));
        body.setTitle("Заголовок сущности");
        body.setVerified(true);

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
        Assertions.assertTrue(isNumeric(response));
    }

    @Test
    @Epic("Управление сущностями")
    @Feature("Удаление сущности")
    @Description("Проверка метода удаления сущности")
    public void deleteEntityTest() throws IOException, InterruptedException {
        int entityId = steps.createEntityStep();

        given()
                .baseUri(URL)
                .pathParam("id", entityId)
                .when()
                .delete("/api/delete/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @Epic("Управление сущностями")
    @Feature("Получение сущности")
    @Description("Проверка метода получения сущности")
    public void getEntityTest() throws IOException, InterruptedException {
        EntityRequest body = new EntityRequest();
        String additionalInfo = "Дополнительные сведения";
        int additionalNumber = 789;
        body.setAddition(new AdditionObj(additionalInfo, additionalNumber));
        body.setImportant_numbers(Arrays.asList(1, 22, 333, 4444));
        body.setTitle("Заголовок новой сущности");
        body.setVerified(false);

        int entityId = steps.createEntityStep(body);

        String response = given()
                .baseUri(URL)
                .pathParam("id", entityId)
                .when()
                .get("/api/get/{id}")
                .then()
                .statusCode(200)
                .extract()
                .asString();

        EntityResponse entityResponse = objectMapper.readValue(response, EntityResponse.class);
        assertAll(
                "Grouped Assertions of getEntity method",
                () -> assertEquals(entityId, entityResponse.getId(), "Id should be " + entityId),
                () -> assertEquals(body.getTitle(), entityResponse.getTitle(), "Title should be " + body.getTitle()),
                () -> assertEquals(body.getVerified(), entityResponse.getVerified(), "Verified should be " + body.getVerified()),
                () -> assertEquals(body.getImportant_numbers(), entityResponse.getImportant_numbers(), "Important numbers should be " + body.getImportant_numbers()),
                () -> assertEquals(entityId, entityResponse.getAdditionId(), "Additional id should be " + entityId),
                () -> assertEquals(additionalInfo, entityResponse.getAdditionalInfo(), "Additional info should be " + additionalInfo),
                () -> assertEquals(additionalNumber, entityResponse.getAdditionalNumber(), "Additional number should be " + additionalNumber)
        );
    }

    @Test
    @Epic("Управление сущностями")
    @Feature("Обновление сущности")
    @Description("Проверка метода обновления сущности")
    public void patchEntityTest() throws IOException, InterruptedException {
        int entityId = steps.createEntityStep();

        EntityRequest body = new EntityRequest();
        String additionalInfo = "Обновленные сведения";
        int additionalNumber = 999;
        body.setAddition(new AdditionObj(additionalInfo, additionalNumber));
        body.setImportant_numbers(Arrays.asList(0, 2));
        body.setTitle("Заголовок обновленной сущности");
        body.setVerified(false);

        given()
                .baseUri(URL)
                .pathParam("id", entityId)
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .patch("/api/patch/{id}")
                .then()
                .statusCode(204);

        EntityResponse entityResponse = steps.getEntityResponseStep(entityId);
        assertAll(
                "Grouped Assertions of patchEntity method",
                () -> assertEquals(entityId, entityResponse.getId(), "Id should be " + entityId),
                () -> assertEquals(body.getTitle(), entityResponse.getTitle(), "Title should be " + body.getTitle()),
                () -> assertEquals(body.getVerified(), entityResponse.getVerified(), "Verified should be " + body.getVerified()),
                () -> assertEquals(body.getImportant_numbers(), entityResponse.getImportant_numbers(), "Important numbers should be " + body.getImportant_numbers()),
                () -> assertEquals(entityId, entityResponse.getAdditionId(), "Additional id should be " + entityId),
                () -> assertEquals(additionalInfo, entityResponse.getAdditionalInfo(), "Additional info should be " + additionalInfo),
                () -> assertEquals(additionalNumber, entityResponse.getAdditionalNumber(), "Additional number should be " + additionalNumber)
        );
    }

    @Test
    @Epic("Управление сущностями")
    @Feature("Получние списка сущностей")
    @Description("Проверка метода получения списка сущностей")
    public void getAllTest() throws IOException, InterruptedException {
        String uniqueTitle = "Случайный заголовок " + UUID.randomUUID();

        EntityRequest firstBody = new EntityRequest();
        firstBody.setTitle(uniqueTitle);
        firstBody.setVerified(false);

        EntityRequest secondBody = new EntityRequest();
        secondBody.setTitle(uniqueTitle);
        secondBody.setVerified(true);

        int firstEntityId = steps.createEntityStep(firstBody);
        int secondEntityId = steps.createEntityStep(secondBody);

        String response = given()
                .baseUri(URL)
                .queryParam("title", uniqueTitle)
                .when()
                .get("/api/getAll")
                .then()
                .statusCode(200)
                .extract()
                .asString();

        EntitiesResponse entitiesResponse = objectMapper.readValue(response, EntitiesResponse.class);
        EntityResponse first = entitiesResponse.getEntity(0);
        EntityResponse second = entitiesResponse.getEntity(1);
        assertAll(
                "Grouped Assertions of getAll method",
                () -> assertEquals(first.getId(), firstEntityId, "First entity id should be " + firstEntityId),
                () -> assertEquals(first.getTitle(), uniqueTitle, "Title should be " + uniqueTitle),
                () -> assertFalse(first.getVerified(), "Verified should be " + false),
                () -> assertEquals(second.getId(), secondEntityId, "Second entity id should be " + secondEntityId),
                () -> assertEquals(second.getTitle(), uniqueTitle, "Title should be " + uniqueTitle),
                () -> assertTrue(second.getVerified(), "Verified should be " + true),
                () -> assertEquals(2, entitiesResponse.getEntity().size())
        );
    }
}
