package tests.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import helpers.PropertyProvider;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import steps.Steps;

public class BaseTest {
    final static String URL = PropertyProvider.getInstance().getProperties("url");
    Steps steps = new Steps();
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    public static void setUp() {
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri(URL)
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .build();
        RestAssured.requestSpecification = requestSpec;
    }
}
