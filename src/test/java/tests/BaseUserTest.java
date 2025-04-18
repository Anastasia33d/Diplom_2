package tests;

import config.UserClient;
import io.restassured.response.Response;
import models.User;
import org.junit.After;
import org.junit.Before;
import utils.UserGenerator;

import static utils.UserTestUtils.clearUserCreateResponse;

public class BaseUserTest {
    protected UserClient userClient;
    protected Response userCreateResponse;
    protected User user;

    @Before
    public void setUp() {
        userClient = new UserClient();
        user = UserGenerator.getRandomUniqUser();
    }

    @After
    public void tearDown() {
        clearUserCreateResponse(userCreateResponse);
    }
}
