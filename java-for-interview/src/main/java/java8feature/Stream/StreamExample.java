package java8feature.Stream;

import java.util.Arrays;
import java.util.List;

public class StreamExample {
    public static void main(String[] args) {
        List<Integer> data = Arrays.asList(12,22,5,6,13);

        List<Integer> finaData = data.stream()            // source
                .filter(x -> x > 10) // intermediate
                .map(x -> x * 2)     // intermediate
                .toList();                  // terminal

        finaData.forEach(System.out::println);
    }
}
