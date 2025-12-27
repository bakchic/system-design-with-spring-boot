package java8feature.methodRef;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MethodRefExample {

    public static void main(String[] args) {
        List<String> data = Arrays.asList("AA","BB","CC");

        data.forEach(System.out::println);

        String originalString = "Hello, World! 123";
        // Convert to lowercase
        String lowerCaseString = originalString.toLowerCase();

        System.out.println("Original String: " + originalString);
        System.out.println("Lowercase String: " + lowerCaseString);

        // Create a Stream of String elements
        Stream<String> namesStream = Stream.of("Alice", "Bob", "Charlie");

        // Use the Stream API to collect the elements into a new ArrayList
        ArrayList<String> namesList = namesStream.collect(Collectors.toCollection(ArrayList::new));

        // Print the resulting ArrayList
        System.out.println("Created ArrayList: " + namesList);
    }
}
