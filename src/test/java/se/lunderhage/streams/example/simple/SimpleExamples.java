package se.lunderhage.streams.example.simple;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.fail;

public class SimpleExamples {

    private final List<String> list = Arrays.asList("aa", "bb", "cc", "dd");

    @Test
    public void findFirst() {
        list.stream()
                .findFirst()
                .ifPresent(System.out::println);
    }

    @Test
    public void findFirstTraditional() {
        if (list.size() > 0) {
            System.out.println(list.get(0));
        }
    }

    @Test
    public void streamOf() {
        Stream.of("aa", "bb", "cc", "dd")
                .findFirst()
                .ifPresent(System.out::println);
    }

    @Test
    public void intStream() {
        IntStream.range(1, 4).forEach(System.out::println);
    }

    @Test
    public void mapAverage() {
        Arrays.stream(new int[]{1, 2, 3, 4})
                .map(n -> 2 * n + 1)
                .average()
                .ifPresent(System.out::println);
    }

    @Test
    public void mapAverageTraditional() {
        int[] integers = new int[]{1, 2, 3, 4};

        int total = 0;

        for (int integer : integers) {
            int n = 2 * integer + 1;
            total += n;
        }

        if (integers.length > 0) {
            System.out.println((float) (total / integers.length));
        }
    }

    @Test
    public void transformObject() {
        Stream.of("item1", "item2", "item3", "item4")
                .map(s -> s.substring(4))
                .mapToInt(Integer::parseInt)
                .max()
                .ifPresent(System.out::println);

    }

    @Test
    public void transformObjectTraditional() {
        List<String> items = Arrays.asList("item1", "item2", "item3", "item4");

        int max = 0;
        for (String item : items) {
            String s = item.substring(4);
            int n = Integer.parseInt(s);
            if (n > max) {
                max = n;
            }
        }

        if (items.size() > 0) {
            System.out.println(max);
        }
    }

    @Test
    public void mapPrimitiveToObject() {
        IntStream.range(0, 3)
                .mapToObj(i -> "a" + i)
                .forEach(System.out::println);
    }

    @Test
    public void mapPrimitiveToObjectTraditional() {
        List<Integer> integers = Arrays.asList(0, 1, 2);

        for (int integer : integers) {
            String s = "a" + integer;
            System.out.println(s);
        }
    }

    @Test
    public void filter() {
        Stream.of("aa", "bb", "cc", "dd", "ee")
                .filter(s -> {
                    System.out.println("Filter: " + s);
                    return true;
                })
                .forEach(s -> {
                    System.out.println("ForEach: " + s);
                });
    }

    @Test
    public void filterAnyMatch() {
        Stream.of("ba", "aa", "ab", "cc", "dd")
                .map(s -> {
                    System.out.println("Map: " + s);
                    return s.toUpperCase();
                })
                .anyMatch(s -> {
                    System.out.println("AnyMatch: " + s);
                    return s.startsWith("A");
                });
    }

    @Test
    public void filterMap() {
        Stream.of("ba", "aa", "ab", "cc", "dd")
                .filter(s -> {
                    System.out.println("Filter: " + s);
                    return s.startsWith("a");
                })
                .map(s -> {
                    System.out.println("Map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> {
                    System.out.println("ForEach: " + s);
                });
    }

    @Test
    public void sortFilterMap() {
        Stream.of("ba", "aa", "ab", "cc", "dd")
                .sorted((s1, s2) -> {
                    return s1.compareTo(s2);
                })
                .filter(s -> {
                    System.out.println("Filter: " + s);
                    return s.startsWith("a");
                })
                .map(s -> {
                    System.out.println("Map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> {
                    System.out.println("ForEach: " + s);
                });
    }

    @Test
    public void filterSortMap() {
        Stream.of("ba", "aa", "ab", "cc", "dd")
                .filter(s -> {
                    System.out.println("Filter: " + s);
                    return s.startsWith("a");
                })
                .sorted((s1, s2) -> {
                    return s1.compareTo(s2);
                })
                .map(s -> {
                    System.out.println("Map: " + s);
                    return s.toUpperCase();
                })
                .forEach(s -> {
                    System.out.println("ForEach: " + s);
                });
    }

    @Test
    public void reuseStream() {
        Stream<String> stream = Stream.of("hello", "hallo", "hola", "hej")
                .filter(s -> s.startsWith("h"));

        // OK
        stream.anyMatch(s -> true);

        try {
            stream.noneMatch(s -> true);
            fail("No exception.");
        } catch (IllegalStateException e) {
            // As excepted!
        }

        Supplier<Stream<String>> supplier = () -> Stream.of("hello", "hallo", "hola", "hej");

        supplier.get().anyMatch(s -> true);
        supplier.get().noneMatch(s -> true);
    }

}
