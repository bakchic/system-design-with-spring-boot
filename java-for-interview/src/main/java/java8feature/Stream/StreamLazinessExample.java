package java8feature.Stream;

import java.util.*;
import java.util.stream.*;

public class StreamLazinessExample {

    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        System.out.println("Stream created...");

        Stream<Integer> stream = numbers.stream()
                .filter(n -> {
                    System.out.println("filter: " + n);
                    return n % 2 == 0;
                })
                .map(n -> {
                    System.out.println("map: " + n);
                    return n * 10;
                });

        System.out.println("No terminal operation yet...");
        System.out.println("--------------------------------");

        // ❌ Nothing happens until terminal operation
        System.out.println("Triggering terminal operation...");
        stream.forEach(n -> System.out.println("forEach: " + n));
    }
}