package utils;
import com.github.javafaker.Faker;
import models.User;


public class UserGenerator {
    private static final Faker faker = new Faker();

    public static User getRandomUniqUser() {
        String email = faker.internet().emailAddress();
        String password = faker.internet().password();
        String name = faker.name().firstName();
        return new User(email, password, name);
    }

    public static User getUserWithoutEmail() {
        String password = faker.internet().password();
        String name = faker.name().firstName();
        return new User(null, password, name);
    }
}



