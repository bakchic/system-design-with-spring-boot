package java8feature.Stream;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class GroupElementByDepartment {
    public static void main(String[] args) {
        List<Employee> employees = List.of(
                new Employee(1, "Alice", "IT", 70000),
                new Employee(2, "Bob", "HR", 50000),
                new Employee(3, "Charlie", "IT", 90000),
                new Employee(4, "David", "Finance", 60000),
                new Employee(5, "Eve", "IT", 80000)
        );

        Map<String, List<Employee>> groupElementByDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));

        groupElementByDept.forEach((key, value) -> {
            System.out.println("key: " + key);
            System.out.println("value: " + value);
        });

        // 7️⃣ Count employees in each department
        Map<String, Long> result = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.counting()));

        result.forEach((key, value) -> {
            System.out.println("key: " + key);
            System.out.println("value: " + value);
        });

        // 8️⃣ Average salary per department

        Map<String, Double> resultAvg = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.averagingInt(Employee::getSalary)));

        resultAvg.forEach((s, employee) -> {
            System.out.println("key: " + s);
            System.out.println("value: " + employee);
        });

        // 9️⃣ Department → Highest paid employee
        Map<String, Optional<Employee>> resultHigh = employees.stream().collect(Collectors.groupingBy(Employee::getDepartment
                , Collectors.maxBy(Comparator.comparingInt(Employee::getSalary))));

        resultHigh.forEach((key, employee) -> {
            System.out.println("key: " + key);
            System.out.println("value: " + employee);
        });
    }
}
