package java8feature.lambda;

import java.util.Date;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class LambdaExample {
    public static void main(String[] args) {
        Predicate<Integer> even = x -> x % 2 == 0; // For condition
        Function<String, Integer> len = String::length; // transform String into integer
        Consumer<String> print = System.out::println; // Only accept value . No return
        Supplier<Date> now = Date::new; // Only produce , not accept data

        System.out.println(even.test(5));
        System.out.println(len.apply("Bapi"));

        print.accept("I am Rudradeep");

        System.out.println(now.get());


    }
}
