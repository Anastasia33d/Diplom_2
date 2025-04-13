package utils;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class BaseTestUtils {
    @Step("Проверяем, что статус-код равен {expectedStatusCode}")
    public static void verifyStatusCodeStep(Response response, int expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
    }

    @Step("Проверяем, что поле {fieldName} соответствует условию")
    public static void verifyFieldStep(Response response, String fieldName, Object matcher) {
        response.then().body(fieldName, (org.hamcrest.Matcher<?>) matcher);
    }
}
