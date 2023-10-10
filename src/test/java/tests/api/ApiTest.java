package tests.api;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import models.EntitiesResponse;
import models.EntityRequest;
import models.EntityResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApiTest extends BaseTest {
    @Test
    @Epic("Управление сущностями")
    @Feature("Создание сущности")
    @Description("Проверка метода создания сущности")
    public void createEntityTest() {
        EntityRequest body = new EntityRequest();
        body.setAddition(new EntityRequest.Addition("Дополнительные сведения", 123));
        body.setImportant_numbers(Arrays.asList(42, 87, 15));
        body.setTitle("Заголовок сущности");
        body.setVerified(true);

        String response = steps.createEntityStep(body);

        assertTrue(isNumeric(response));
    }

    @Test
    @Epic("Управление сущностями")
    @Feature("Удаление сущности")
    @Description("Проверка метода удаления сущности")
    public void deleteEntityTest() {
        int entityId = Integer.parseInt(steps.createEntityStep());

        int statusCode = steps.deleteEntityStep(entityId);

        assertEquals(204, statusCode, "Status code should be 204");
    }

    @Test
    @Epic("Управление сущностями")
    @Feature("Получение сущности")
    @Description("Проверка метода получения сущности")
    public void getEntityTest() throws IOException {
        EntityRequest body = new EntityRequest();
        String additionalInfo = "Дополнительные сведения";
        int additionalNumber = 789;
        body.setAddition(new EntityRequest.Addition(additionalInfo, additionalNumber));
        body.setImportant_numbers(Arrays.asList(1, 22, 333, 4444));
        body.setTitle("Заголовок новой сущности");
        body.setVerified(false);

        int entityId = Integer.parseInt(steps.createEntityStep(body));
        EntityResponse entityResponse = steps.getEntityResponseStep(entityId);

        assertThat(entityResponse).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(body);
    }

    @Test
    @Epic("Управление сущностями")
    @Feature("Обновление сущности")
    @Description("Проверка метода обновления сущности")
    public void patchEntityTest() throws IOException, InterruptedException {
        int entityId = Integer.parseInt(steps.createEntityStep());

        EntityRequest body = new EntityRequest();
        String additionalInfo = "Обновленные сведения";
        int additionalNumber = 999;
        body.setAddition(new EntityRequest.Addition(additionalInfo, additionalNumber));
        body.setImportant_numbers(Arrays.asList(0, 2));
        body.setTitle("Заголовок обновленной сущности");
        body.setVerified(false);

        int statusCode = steps.patchEntityStep(entityId, body);
        assertEquals(204, statusCode, "Status code should be 204");

        EntityResponse entityResponse = steps.getEntityResponseStep(entityId);
        assertThat(entityResponse).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(body);
    }

    @Test
    @Epic("Управление сущностями")
    @Feature("Получние списка сущностей")
    @Description("Проверка метода получения списка сущностей")
    public void getAllTest() throws IOException {
        String uniqueTitle = "Случайный заголовок " + UUID.randomUUID();

        EntityRequest firstBody = new EntityRequest();
        firstBody.setTitle(uniqueTitle);
        firstBody.setVerified(false);

        EntityRequest secondBody = new EntityRequest();
        secondBody.setTitle(uniqueTitle);
        secondBody.setVerified(true);

        steps.createEntityStep(firstBody);
        steps.createEntityStep(secondBody);

        EntitiesResponse entitiesResponse = steps.getListOfEntitiesStep("title", uniqueTitle);
        EntityResponse first = entitiesResponse.getEntity(0);
        EntityResponse second = entitiesResponse.getEntity(1);
        assertEquals(2, entitiesResponse.getEntity().size(), "Should return 2 entities");
        assertThat(first).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(firstBody);
        assertThat(second).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .isEqualTo(secondBody);
    }
}
