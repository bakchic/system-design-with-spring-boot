package java8feature.Stream;

import java.util.*;
import java.util.stream.*;

public class ReductionAndCollectorsExample {

    public static void main(String[] args) {

        List<Employee> employees = List.of(
                new Employee(1, "Alice", "IT", 70000),
                new Employee(2, "Bob", "HR", 50000),
                new Employee(3, "Charlie", "IT", 90000),
                new Employee(4, "David", "Finance", 60000),
                new Employee(5, "Eve", "IT", 80000)
        );

        /* =========================
           🔹 REDUCTION (reduce)
           ========================= */

        // 1️⃣ Sum of all salaries
        int totalSalary = employees.stream()
                .map(Employee::getSalary)
                .reduce(0, Integer::sum);

        System.out.println("Total Salary = " + totalSalary);

        // 2️⃣ Highest salary
        Optional<Integer> maxSalary = employees.stream()
                .map(Employee::getSalary)
                .reduce(Integer::max);

        maxSalary.ifPresent(s -> System.out.println("Max Salary = " + s));

        // 3️⃣ Combine employee names into a single string
        String allNames = employees.stream()
                .map(Employee::getName)
                .reduce("", (a, b) -> a + b + ", ");

        System.out.println("All Names = " + allNames);

        /* =========================
           🔹 COLLECTORS
           ========================= */

        // 4️⃣ Collect names into List
        List<String> names = employees.stream()
                .map(Employee::getName)
                .toList();

        System.out.println("\nNames List = " + names);

        // 5️⃣ Collect unique departments into Set
        Set<String> departments = employees.stream()
                .map(Employee::getDepartment)
                .collect(Collectors.toSet());

        System.out.println("Departments Set = " + departments);

        // 6️⃣ Group employees by department
        Map<String, List<Employee>> employeesByDept =
                employees.stream()
                        .collect(Collectors.groupingBy(Employee::getDepartment));

        System.out.println("\nEmployees Grouped by Department:");
        employeesByDept.forEach((dept, empList) ->
                System.out.println(dept + " -> " + empList));

        // 7️⃣ Count employees in each department
        Map<String, Long> countByDept =
                employees.stream()
                        .collect(Collectors.groupingBy(
                                Employee::getDepartment,
                                Collectors.counting()
                        ));

        System.out.println("\nEmployee Count by Department = " + countByDept);

        // 8️⃣ Average salary per department
        Map<String, Double> avgSalaryByDept =
                employees.stream()
                        .collect(Collectors.groupingBy(
                                Employee::getDepartment,
                                Collectors.averagingInt(Employee::getSalary)
                        ));

        System.out.println("\nAverage Salary by Department = " + avgSalaryByDept);

        // 9️⃣ Department → Highest paid employee
        Map<String, Optional<Employee>> highestPaidByDept =
                employees.stream()
                        .collect(Collectors.groupingBy(
                                Employee::getDepartment,
                                Collectors.maxBy(
                                        Comparator.comparingInt(Employee::getSalary)
                                )
                        ));

        System.out.println("\nHighest Paid Employee by Department:");
        highestPaidByDept.forEach((dept, emp) ->
                System.out.println(dept + " -> " + emp.orElse(null)));

        // 🔟 Convert to Map (id → name)
        Map<Integer, String> idNameMap =
                employees.stream()
                        .collect(Collectors.toMap(
                                Employee::getId,
                                Employee::getName
                        ));

        System.out.println("\nID → Name Map = " + idNameMap);

        // 1️⃣1️⃣ Joining (Collector reduction)
        String joinedNames =
                employees.stream()
                        .map(Employee::getName)
                        .collect(Collectors.joining(" | "));

        System.out.println("\nJoined Names = " + joinedNames);
    }
}

