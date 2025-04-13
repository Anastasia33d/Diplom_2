package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import models.Order;
import models.User;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import utils.OrderGenerator;
import utils.UserGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static utils.BaseTestUtils.verifyFieldStep;
import static utils.BaseTestUtils.verifyStatusCodeStep;
import static utils.OrderTestUtils.createOrderStep;
import static utils.OrderTestUtils.createOrderWithoutAuthStep;
import static utils.UserTestUtils.clearUserCreateResponse;
import static utils.UserTestUtils.createUserStep;
import constants.OrderConstants;

public class OrderTest {
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
    @DisplayName("Создание заказа с авторизацией")
    @Description("Проверяем успешное создание заказа авторизованным пользователем")
    public void createOrderWithAuthTest() {
        userCreateResponse = createUserStep(user);
        Response response = createOrderStep(OrderGenerator.getRandomOrder(), userCreateResponse.path("accessToken"));

        verifyStatusCodeStep(response, HttpStatus.SC_OK);
        verifyFieldStep(response, "success", equalTo(true));
        verifyFieldStep(response, "order.number", notNullValue());
        verifyFieldStep(response, "name", notNullValue());
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    @Description("Проверяем создание заказа без авторизации")
    public void createOrderWithoutAuthTest() {
        // Получаем успешный ответ со статусом 200. Баг?
        verifyStatusCodeStep(createOrderWithoutAuthStep(OrderGenerator.getRandomOrder()), HttpStatus.SC_UNAUTHORIZED);
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    @Description("Проверяем создание заказа с невалидным хешем ингредиентов")
    public void createOrderWithInvalidIngredientsTest() {
        userCreateResponse = createUserStep(user);
        Response response = createOrderStep(new Order(Arrays.asList("invalid_hash_1", "invalid_hash_2")),  userCreateResponse.path("accessToken"));
        verifyStatusCodeStep(response, HttpStatus.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    @Description("Проверяем создание заказа с пустым списком ингредиентов")
    public void createOrderWithoutIngredientsTest() {
        userCreateResponse = createUserStep(user);
        Response response = createOrderStep(new Order(new ArrayList<>()), userCreateResponse.path("accessToken"));

        verifyStatusCodeStep(response, HttpStatus.SC_BAD_REQUEST);
        verifyFieldStep(response,"success", equalTo(false));
        verifyFieldStep(response, "message", equalTo(OrderConstants.INGREDIENTS_FIELD_REQUIRED_MESSAGE));
    }
}