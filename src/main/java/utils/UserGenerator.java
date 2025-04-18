package utils;
import com.github.javafaker.Faker;
import models.User;

public class UserGenerator {
    private static final Faker faker = new Faker();

    private static String generateEmail() {
        return faker.internet().emailAddress();
    }
    private static String generatePassword() {
        return faker.internet().password();
    }
    private static String generateName() {
        return faker.name().firstName();
    }
    public static User getRandomUniqUser() {
        return new User(generateEmail(), generatePassword(), generateName());
    }
    public static User getUserWithoutEmail() {
        return new User(null, generatePassword(), generateName());
    }
    public static User getUserWithoutPassword() {
        return new User(generateEmail(), null, generateName());
    }
    public static User getUserWithoutName() {
        return new User(generateEmail(), generatePassword(), null);
    }
}