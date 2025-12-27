package java8feature.Stream;

import java.util.*;
import java.util.stream.*;

public class ParallelStreamSafeExample {

    public static void main(String[] args) {

        List<Integer> numbers =
                IntStream.rangeClosed(1, 1_000_000)
                        .boxed()
                        .toList();

        // Sequential
        long startSeq = System.currentTimeMillis();
        long seqSum =
                numbers.stream()
                        .reduce(0, Integer::sum);
        long seqTime = System.currentTimeMillis() - startSeq;

        // Parallel
        long startPar = System.currentTimeMillis();
        long parSum =
                numbers.parallelStream()
                        .reduce(0, Integer::sum);
        long parTime = System.currentTimeMillis() - startPar;

        System.out.println("Sequential Sum = " + seqSum + " | Time = " + seqTime);
        System.out.println("Parallel Sum   = " + parSum + " | Time = " + parTime);
    }
}