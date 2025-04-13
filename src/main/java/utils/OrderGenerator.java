package utils;

import models.Order;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class OrderGenerator {
    private static final int MAX_INGREDIENTS_COUNT = 5;
    private static final List<String> AVAILABLE_INGREDIENTS = Arrays.asList(
            "61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f",
            "61c0c5a71d1f82001bdaaa70", "61c0c5a71d1f82001bdaaa71",
            "61c0c5a71d1f82001bdaaa72", "61c0c5a71d1f82001bdaaa6e",
            "61c0c5a71d1f82001bdaaa73", "61c0c5a71d1f82001bdaaa74",
            "61c0c5a71d1f82001bdaaa6c", "61c0c5a71d1f82001bdaaa75",
            "61c0c5a71d1f82001bdaaa76", "61c0c5a71d1f82001bdaaa77",
            "61c0c5a71d1f82001bdaaa78", "61c0c5a71d1f82001bdaaa79",
            "61c0c5a71d1f82001bdaaa7a"
    );

    public static Order getRandomOrder() {
        Random random = new Random();
        int ingredientsCount = random.nextInt(MAX_INGREDIENTS_COUNT);
        List<String> selectedIngredients = new ArrayList<>();

        for (int i = 0; i < ingredientsCount; i++) {
            String ingredient = AVAILABLE_INGREDIENTS.get(random.nextInt(AVAILABLE_INGREDIENTS.size()));
            selectedIngredients.add(ingredient);
        }

        return new Order(selectedIngredients);
    }
}