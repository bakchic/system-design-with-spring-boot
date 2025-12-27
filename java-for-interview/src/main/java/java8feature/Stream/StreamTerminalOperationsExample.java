package java8feature.Stream;

import java.util.*;
import java.util.stream.*;

public class StreamTerminalOperationsExample {

    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // 1️⃣ forEach → returns void (used for side effects)
        System.out.println("forEach:");
        numbers.stream()
                .forEach(n -> System.out.println(n));

        // 2️⃣ collect → returns a Collection
        List<Integer> squaredNumbers =
                numbers.stream()
                        .map(n -> n * n)
                        .collect(Collectors.toList());
        System.out.println("\ncollect:");
        System.out.println(squaredNumbers);

        // 3️⃣ reduce → returns a single value
        int sum =
                numbers.stream()
                        .reduce(0, Integer::sum);
        System.out.println("\nreduce:");
        System.out.println("Sum = " + sum);

        // 4️⃣ count → returns long
        long count =
                numbers.stream()
                        .filter(n -> n > 3)
                        .count();
        System.out.println("\ncount:");
        System.out.println("Count of numbers > 3 = " + count);

        // 5️⃣ anyMatch / allMatch → returns boolean
        boolean anyGreaterThanFour =
                numbers.stream()
                        .anyMatch(n -> n > 4);

        boolean allPositive =
                numbers.stream()
                        .allMatch(n -> n > 0);

        System.out.println("\nanyMatch / allMatch:");
        System.out.println("Any number > 4? " + anyGreaterThanFour);
        System.out.println("All numbers positive? " + allPositive);
    }
}