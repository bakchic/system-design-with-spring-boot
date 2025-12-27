package java8feature;

import java.util.concurrent.*;
import java.util.*;

public class CompletableFutureFullExample {

    public static void main(String[] args) throws Exception {

        /* =========================================================
           1️⃣ Creating CompletableFuture (Async Task)
           ========================================================= */

        CompletableFuture<String> future =
                CompletableFuture.supplyAsync(() -> {
                    // Runs in ForkJoinPool.commonPool()
                    sleep(1000);
                    return "User Data";
                });

        // get() blocks the current thread until result is available
        System.out.println("Result = " + future.get());

        /* =========================================================
           2️⃣ thenApply() – Transform the result
           ========================================================= */

        CompletableFuture<Integer> lengthFuture =
                CompletableFuture.supplyAsync(() -> "Hello CompletableFuture")
                        .thenApply(result -> {
                            // Transforms result (String → Integer)
                            return result.length();
                        });

        System.out.println("Length = " + lengthFuture.get());

        /* =========================================================
           3️⃣ thenAccept() – Consume result (no return)
           ========================================================= */

        CompletableFuture<Void> consumeFuture =
                CompletableFuture.supplyAsync(() -> "Email Sent")
                        .thenAccept(result -> {
                            // Consumes result, returns nothing
                            System.out.println("Consumed: " + result);
                        });

        consumeFuture.get(); // wait

        /* =========================================================
           4️⃣ thenRun() – Run independent task
           ========================================================= */

        CompletableFuture<Void> runFuture =
                CompletableFuture.supplyAsync(() -> "Task Done")
                        .thenRun(() -> {
                            // Does NOT get previous result
                            System.out.println("Logging completed");
                        });

        runFuture.get();

        /* =========================================================
           5️⃣ thenCompose() – Dependent async tasks (FLATMAP)
           ========================================================= */

        CompletableFuture<String> composedFuture =
                getUserId()
                        .thenCompose(userId ->
                                getUserDetails(userId)
                        );

        System.out.println("User Details = " + composedFuture.get());

        /* =========================================================
           6️⃣ thenCombine() – Combine two independent async tasks
           ========================================================= */

        CompletableFuture<String> combinedFuture =
                getUserName()
                        .thenCombine(getUserAge(),
                                (name, age) -> name + " is " + age + " years old"
                        );

        System.out.println(combinedFuture.get());

        /* =========================================================
           7️⃣ allOf() – Wait for ALL tasks
           ========================================================= */

        CompletableFuture<Void> allTasks =
                CompletableFuture.allOf(
                        getUserName(),
                        getUserAge(),
                        getUserCity()
                );

        // Blocks until all futures complete
        allTasks.get();
        System.out.println("All tasks completed");

        /* =========================================================
           8️⃣ anyOf() – First completed task
           ========================================================= */

        CompletableFuture<Object> anyTask =
                CompletableFuture.anyOf(
                        getSlowTask(),
                        getFastTask()
                );

        System.out.println("First completed = " + anyTask.get());

        /* =========================================================
           9️⃣ Exception Handling – exceptionally()
           ========================================================= */

        CompletableFuture<String> errorHandled =
                CompletableFuture.supplyAsync(() -> {
                    if (true) {
                        throw new RuntimeException("Something went wrong");
                    }
                    return "Success";
                }).exceptionally(ex -> {
                    // Handles exception and returns fallback value
                    return "Fallback Value";
                });

        System.out.println(errorHandled.get());

        /* =========================================================
           🔟 handle() – Handle success & failure
           ========================================================= */

        CompletableFuture<String> handled =
                CompletableFuture.supplyAsync(() -> "Result")
                        .handle((res, ex) -> {
                            if (ex != null) {
                                return "Error handled";
                            }
                            return res + " processed";
                        });

        System.out.println(handled.get());

        /* =========================================================
           1️⃣1️⃣ Custom Executor (IMPORTANT)
           ========================================================= */

        ExecutorService executor = Executors.newFixedThreadPool(2);

        CompletableFuture<String> customExecutorFuture =
                CompletableFuture.supplyAsync(() -> {
                    return "Running in custom executor";
                }, executor);

        System.out.println(customExecutorFuture.get());

        executor.shutdown();
    }

    /* =========================================================
       Helper async methods
       ========================================================= */

    static CompletableFuture<Integer> getUserId() {
        return CompletableFuture.supplyAsync(() -> 101);
    }

    static CompletableFuture<String> getUserDetails(int userId) {
        return CompletableFuture.supplyAsync(() ->
                "UserDetails for id " + userId
        );
    }

    static CompletableFuture<String> getUserName() {
        return CompletableFuture.supplyAsync(() -> "Alice");
    }

    static CompletableFuture<Integer> getUserAge() {
        return CompletableFuture.supplyAsync(() -> 30);
    }

    static CompletableFuture<String> getUserCity() {
        return CompletableFuture.supplyAsync(() -> "Sydney");
    }

    static CompletableFuture<String> getSlowTask() {
        return CompletableFuture.supplyAsync(() -> {
            sleep(2000);
            return "Slow Task";
        });
    }

    static CompletableFuture<String> getFastTask() {
        return CompletableFuture.supplyAsync(() -> {
            sleep(500);
            return "Fast Task";
        });
    }

    static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }
}
