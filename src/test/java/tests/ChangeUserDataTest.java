package tests;
import constants.UserConstants;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import utils.UserGenerator;
import static org.hamcrest.CoreMatchers.equalTo;
import static utils.BaseTestUtils.verifyFieldStep;
import static utils.BaseTestUtils.verifyStatusCodeStep;
import static utils.UserTestUtils.createUserStep;
import static utils.UserTestUtils.updateUserStep;

public class ChangeUserDataTest extends BaseUserTest {
    private String accessToken;

    @Before
    public void setUp() {
        super.setUp();
        userCreateResponse = createUserStep(user);
        accessToken = userCreateResponse.path("accessToken");
    }

    @Test
    @DisplayName("Изменение email авторизованного пользователя")
    @Description("Проверяем успешное изменение email авторизованного пользователя")
    public void changeEmailWithAuthTest() {
        String newEmail = UserGenerator.getRandomUniqUser().getEmail();
        user.setEmail(newEmail);

        Response updateResponse = updateUserStep(accessToken, user);
        verifyStatusCodeStep(updateResponse, HttpStatus.SC_OK);
        verifyFieldStep(updateResponse, "success", equalTo(true));
        verifyFieldStep(updateResponse, "user.email", equalTo(newEmail));
    }

    @Test
    @DisplayName("Изменение имени авторизованного пользователя")
    @Description("Проверяем успешное изменение имени авторизованного пользователя")
    public void changeNameWithAuthTest() {
        String newName = UserGenerator.getRandomUniqUser().getName();
        user.setName(newName);

        Response updateResponse = updateUserStep(accessToken, user);
        verifyStatusCodeStep(updateResponse, HttpStatus.SC_OK);
        verifyFieldStep(updateResponse, "success", equalTo(true));
        verifyFieldStep(updateResponse, "user.name", equalTo(newName));
    }

    @Test
    @DisplayName("Изменение email неавторизованного пользователя")
    @Description("Проверяем что система возвращает ошибку при попытке изменения email без авторизации")
    public void changeEmailWithoutAuthTest() {
        String newEmail = UserGenerator.getRandomUniqUser().getEmail();
        user.setEmail(newEmail);

        Response updateResponse = updateUserStep("", user);
        verifyStatusCodeStep(updateResponse, HttpStatus.SC_UNAUTHORIZED);
        verifyFieldStep(updateResponse, "success", equalTo(false));
        verifyFieldStep(updateResponse, "message", equalTo(UserConstants.NOT_AUTHORISED_MESSAGE));
    }

    @Test
    @DisplayName("Изменение имени неавторизованного пользователя")
    @Description("Проверяем что система возвращает ошибку при попытке изменения имени без авторизации")
    public void changeNameWithoutAuthTest() {
        String newName = UserGenerator.getRandomUniqUser().getName();
        user.setName(newName);

        Response updateResponse = updateUserStep("", user);
        verifyStatusCodeStep(updateResponse, HttpStatus.SC_UNAUTHORIZED);
        verifyFieldStep(updateResponse, "success", equalTo(false));
        verifyFieldStep(updateResponse, "message", equalTo(UserConstants.NOT_AUTHORISED_MESSAGE));
    }
}