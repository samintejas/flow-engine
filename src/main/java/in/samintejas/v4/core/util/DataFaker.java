package in.samintejas.v4.core.util;

import com.github.javafaker.Faker;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class DataFaker {

    @Getter
    private static final Faker faker = new Faker();
    private DataFaker() {}

    public static String generateRandomVal(String key) {
        return switch (key) {
            case "name" -> faker.name().fullName();
            case "city" -> faker.address().city();
            case "street" -> faker.address().streetAddress();
            default -> "Unknown";
        };
    }

    public static String generateRandomTime(String key) {
        String[] components = key.split("-");
        String type = components[0];
        String format = components[1];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        LocalDateTime randomTime;

        return switch (type) {
            case "now" -> LocalDateTime.now().format(formatter); // Current time
            case "past" -> {
                randomTime = LocalDateTime.now().minusDays(ThreadLocalRandom.current().nextInt(1, 3652));
                yield randomTime.format(formatter);
            }
            case "future" -> {
                randomTime = LocalDateTime.now().plusDays(ThreadLocalRandom.current().nextInt(1, 3652));
                yield randomTime.format(formatter);
            }
            default -> "Invalid time key";
        };
    }

}
