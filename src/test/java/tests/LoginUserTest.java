package tests;
import constants.UserConstants;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static utils.BaseTestUtils.verifyFieldStep;
import static utils.BaseTestUtils.verifyStatusCodeStep;
import static utils.UserTestUtils.createUserStep;
import static utils.UserTestUtils.loginUserStep;

public class LoginUserTest extends BaseUserTest {

    @Before
    public void createUser() {
        userCreateResponse = createUserStep(user);
    }

    @Test
    @DisplayName("Успешная авторизация существующего пользователя")
    @Description("Проверяем успешную авторизацию существующего пользователя с корректными данными")
    public void loginExistingUserTest() {
        Response loginResponse = loginUserStep(user);
        verifyStatusCodeStep(loginResponse, HttpStatus.SC_OK);
        verifyFieldStep(loginResponse, "success", equalTo(true));
        verifyFieldStep(loginResponse, "accessToken", notNullValue());
        verifyFieldStep(loginResponse, "refreshToken", notNullValue());
        verifyFieldStep(loginResponse, "user.email", equalTo(user.getEmail()));
        verifyFieldStep(loginResponse, "user.name", equalTo(user.getName()));
    }

    @Test
    @DisplayName("Авторизация с неверными учетными данными")
    @Description("Проверяем что система возвращает ошибку при попытке авторизации с неверным паролем")
    public void loginWithWrongCredentialsTest() {
        user.setPassword("wrongPassword");
        Response loginResponse = loginUserStep(user);
        verifyStatusCodeStep(loginResponse, HttpStatus.SC_UNAUTHORIZED);
        verifyFieldStep(loginResponse, "success", equalTo(false));
        verifyFieldStep(loginResponse, "message", equalTo(UserConstants.WRONG_CREDENTIALS_MESSAGE));
    }
}