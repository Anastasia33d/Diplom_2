package utils;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.User;
import config.UserClient;
import org.apache.http.HttpStatus;

public class UserTestUtils {
    private static final UserClient userClient = new UserClient();

    @Step("Удаляем пользователя с токеном: {accessToken}")
    public static void deleteUser(String accessToken) {
        userClient.delete(accessToken);
    }
    @Step("Создаем пользователя: {user}")
    public static Response createUserStep(User user) {
        return userClient.create(user);
    }
    @Step("Логинимся под пользователем: {user}")
    public static Response loginUserStep(User user) {
        return userClient.login(user);
    }
    @Step("Обновляем данные пользователя: accessToken - {accessToken}, пользователь - {user}")
    public static Response updateUserStep(String accessToken, User user) {
        return userClient.update(accessToken, user);
    }

    @Step(("Очищаем данные пользователя: {response}"))
    public static void clearUserCreateResponse(Response response) {
        if (!isUserCreated(response)) {
            return;
        }
        String accessToken = response.path("accessToken");
        if(accessToken != null) {
            deleteUser(accessToken);
        }
    }

    public static Boolean isUserCreated(Response response) {
        return response != null && response.statusCode() == HttpStatus.SC_OK;
    }
}