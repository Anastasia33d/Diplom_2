package utils;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.Order;
import config.OrderClient;

public class OrderTestUtils {
    private static final OrderClient orderClient = new OrderClient();

    @Step("Создаем заказ с ингредиентами: {order}, accessToken: {accessToken}")
    public static Response createOrderStep(Order order, String accessToken) {
        return orderClient.createOrder(order, accessToken);
    }

    @Step("Создаем заказ без авторизации: {order}")
    public static Response createOrderWithoutAuthStep(Order order) {
        return orderClient.createOrder(order, null);
    }

    @Step("Получаем заказы пользователя с токеном: {accessToken}")
    public static Response getUserOrdersStep(String accessToken) {
        return orderClient.getUserOrders(accessToken);
    }
}
