package tests;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import models.User;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.OrderGenerator;
import utils.UserGenerator;
import static org.hamcrest.Matchers.*;
import static utils.BaseTestUtils.verifyFieldStep;
import static utils.BaseTestUtils.verifyStatusCodeStep;
import static utils.OrderTestUtils.*;
import static utils.UserTestUtils.*;
import constants.UserConstants;

public class GetUserOrdersTest {
    private Response userCreateResponse;
    private User user;

    @Before
    public void setUp() {
        user = UserGenerator.getRandomUniqUser();
    }

    @After
    public void tearDown() {
        clearUserCreateResponse(userCreateResponse);
    }

    @Test
    @DisplayName("Получение заказов авторизованного пользователя")
    @Description("Проверяем успешное получение заказов авторизованным пользователем")
    public void getUserOrdersWithAuthTest() {
        userCreateResponse = createUserStep(user);
        String accessToken = userCreateResponse.path("accessToken");

        createOrderStep(OrderGenerator.getRandomOrder(), accessToken);
        Response response = getUserOrdersStep(accessToken);

        verifyStatusCodeStep(response, HttpStatus.SC_OK);
        verifyFieldStep(response, "success", equalTo(true));
        verifyFieldStep(response, "orders", notNullValue());
        verifyFieldStep(response, "total", greaterThanOrEqualTo(1));
        verifyFieldStep(response, "totalToday", greaterThanOrEqualTo(1));
    }
    @Test
    @DisplayName("Получение заказов неавторизованным пользователем")
    @Description("Проверяем, что неавторизованный пользователь не может получить заказы")
    public void getUserOrdersWithoutAuthTest() {
        Response response = getUserOrdersStep(null);

        verifyStatusCodeStep(response, HttpStatus.SC_UNAUTHORIZED);
        verifyFieldStep(response, "success", equalTo(false));
        verifyFieldStep(response, "message", equalTo(UserConstants.NOT_AUTHORISED_MESSAGE));
    }
}