package tests;

import constants.UserConstants;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Test;
import utils.UserGenerator;
import static org.hamcrest.CoreMatchers.equalTo;
import static utils.BaseTestUtils.verifyFieldStep;
import static utils.BaseTestUtils.verifyStatusCodeStep;
import static utils.UserTestUtils.clearUserCreateResponse;
import static utils.UserTestUtils.createUserStep;

public class CreateUserTest extends BaseUserTest {
    @Test
    @DisplayName("Создание уникального пользователя")
    @Description("Проверка успешного создания нового пользователя с уникальными данными")
    public void createUniqueUserTest() {
        userCreateResponse = createUserStep(UserGenerator.getRandomUniqUser());

        verifyStatusCodeStep(userCreateResponse,  HttpStatus.SC_OK);
        verifyFieldStep(userCreateResponse, "success", equalTo(true));
    }

    @Test
    @DisplayName("Создание уже зарегистрированного пользователя")
    @Description("Проверка, что создание уже существующего пользователя возвращает 403 Forbidden с сообщением 'User already exists'")
    public void createAlreadyRegisteredUserTest() {
        userCreateResponse = createUserStep(user);

        verifyStatusCodeStep(userCreateResponse,  HttpStatus.SC_OK);
        verifyFieldStep(userCreateResponse, "success", equalTo(true));

        Response secondUserCreateResponse = createUserStep(user);

        verifyStatusCodeStep(secondUserCreateResponse,  HttpStatus.SC_FORBIDDEN);
        verifyFieldStep(secondUserCreateResponse, "success", equalTo(false));
        verifyFieldStep(secondUserCreateResponse, "message", equalTo(UserConstants.ALREADY_EXISTS_MESSAGE));

        clearUserCreateResponse(secondUserCreateResponse);
    }

    @Test
    @DisplayName("Создание пользователя без email")
    @Description("Проверка невозможности создания пользователя без указания обязательного поля email")
    public void createUserWithoutRequiredFieldTest() {
        userCreateResponse = createUserStep(UserGenerator.getUserWithoutEmail());

        verifyStatusCodeStep(userCreateResponse,  HttpStatus.SC_FORBIDDEN);
        verifyFieldStep(userCreateResponse, "success", equalTo(false));
        verifyFieldStep(userCreateResponse, "message", equalTo(UserConstants.REQUIRED_FIELD_MESSAGE));
    }
}