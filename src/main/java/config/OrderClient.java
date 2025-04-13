 package config;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class OrderClient extends BaseClient {
    private static final String ORDERS_PATH = "/orders";

    public Response createOrder(Object ingredients, String accessToken) {
        var requestSpec = given()
                .spec(getBaseSpec())
                .body(ingredients);

        if (accessToken != null) {
            requestSpec.header("Authorization", accessToken);
        }

        return requestSpec
                .when()
                .post(ORDERS_PATH);
    }

    public Response getUserOrders(String accessToken) {
        var requestSpec = given()
                .spec(getBaseSpec());
        if (accessToken != null) {
            requestSpec.header("Authorization", accessToken);
        }
        return requestSpec.get(ORDERS_PATH);
    }
}