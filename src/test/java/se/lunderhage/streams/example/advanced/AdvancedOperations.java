package se.lunderhage.streams.example.advanced;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.summingInt;

public class AdvancedOperations {

    public static class Dog {
        String name;
        String breed;

        public Dog(String name, String breed) {
            this.name = name;
            this.breed = breed;
        }

        public String toString() {
            return name;
        }
    }

    public static class Dogs {
        public static List<Dog> get() {
            return Arrays.asList(
                    new Dog("Pricken", "Dalmador"),
                    new Dog("Miro", "Poodle"),
                    new Dog("Lollo", "Chinese Crested"),
                    new Dog("Ante", "Chinese Crested"),
                    new Dog("Sandra", "Chinese Crested"),
                    new Dog("Jenna", "Norfolk Terrier"),
                    new Dog("Poppy", "Norfolk Terrier"),
                    new Dog("Justin", "Whippet"));
        }
    }

    @Test
    public void collect() {
        List<Dog> filtered =
                Dogs.get().stream()
                .filter(d -> d.name.startsWith(("J")))
                .collect(Collectors.toList());

        System.out.println(filtered);
    }

    @Test
    public void dogsByBreed() {
        Map<String, List<Dog>> dogsByBreed = Dogs.get().stream().collect(Collectors.groupingBy(d -> d.breed));

        dogsByBreed.forEach((breed, name) -> System.out.format("Breed: %s: %s\n", breed, name));
    }

    @Test
    public void countByBreed() {
        Map<String, Integer> counts = Dogs.get().stream()
                .collect(Collectors.toConcurrentMap(d -> d.breed, d -> 1, Integer::sum));

        counts.forEach((breed, count) -> System.out.format("%s: %d\n", breed, count));
    }
}
