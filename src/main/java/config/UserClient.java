package config;

import models.User;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserClient extends BaseClient {
    private static final String BASE_AUTH_PATH = "/auth";
    private static final String CREATE_PATH = BASE_AUTH_PATH + "/register";
    private static final String USER_PATH = BASE_AUTH_PATH + "/user";
    private static final String LOGIN_PATH = BASE_AUTH_PATH + "/login";

    public Response create(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(CREATE_PATH);
    }

    public void delete(String accessToken) {
        given()
                .spec(getBaseSpec())
                .when()
                .header("Authorization", accessToken)
                .delete(USER_PATH);
    }

    public Response login(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(LOGIN_PATH);
    }

    public Response update(String accessToken, User user) {
        return given()
                .spec(getBaseSpec())
                .header("Authorization", accessToken)
                .body(user)
                .when()
                .patch(USER_PATH);
    }
}
