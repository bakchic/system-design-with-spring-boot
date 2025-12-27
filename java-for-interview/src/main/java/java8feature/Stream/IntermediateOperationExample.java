package java8feature.Stream;

import java.util.List;

public class IntermediateOperationExample {
    public static void main(String[] args) {
        List<Integer> evenNumbers =
                List.of(1, 2, 3, 4, 5)
                        .stream()
                        .filter(n -> n % 2 == 0)
                        .toList();

        evenNumbers.forEach(System.out::println);

        List<String> upperCaseNames =
                List.of("java", "spring")
                        .stream()
                        .map(String::toUpperCase)
                        .toList();

        System.out.println("--------------------------------");

        upperCaseNames.forEach(System.out::println);

        List<String> words =
                List.of(List.of("Java", "Kafka"), List.of("Spring", "Docker"))
                        .stream()
                        .flatMap(List::stream)
                        .toList();


        System.out.println("--------------------------------");
        words.forEach(System.out::println);


        List<Integer> uniqueNumbers =
                List.of(1, 2, 2, 3, 3, 4)
                        .stream()
                        .distinct()
                        .toList();


        System.out.println("--------------------------------");
        uniqueNumbers.forEach(System.out::println);


        List<Integer> sortedNumbers =
                List.of(5, 3, 1, 4)
                        .stream()
                        .sorted()
                        .toList();

        System.out.println("--------------------------------");
        sortedNumbers.forEach(System.out::println);


        List<Integer> sliced =
                List.of(1, 2, 3, 4, 5)
                        .stream()
                        .skip(2)
                        .limit(2)
                        .toList();

        System.out.println("--------------------------------");
        sliced.forEach(System.out::println);
    }
}
