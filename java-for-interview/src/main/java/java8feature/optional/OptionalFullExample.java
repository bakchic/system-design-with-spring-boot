package java8feature.optional;

import java.util.*;

public class OptionalFullExample {

    public static void main(String[] args) {

        User userWithEmail = new User("Alice", "alice@gmail.com");
        User userWithoutEmail = new User("Bob", null);

        /* =========================
           1️⃣ Creating Optional
           ========================= */

        Optional<String> opt1 = Optional.of("Hello");   // ❌ null NOT allowed
        Optional<String> opt2 = Optional.ofNullable(null); // ✅ allowed
        Optional<String> opt3 = Optional.empty();       // empty Optional

        System.out.println(opt1);
        System.out.println(opt2);
        System.out.println(opt3);

        /* =========================
           2️⃣ isPresent() & isEmpty()
           ========================= */

        Optional<String> emailOpt =
                Optional.ofNullable(userWithEmail.getEmail());

        if (emailOpt.isPresent()) {
            System.out.println("Email = " + emailOpt.get());
        }

        if (emailOpt.isEmpty()) {
            System.out.println("Email not present");
        }

        /* =========================
           3️⃣ ifPresent()
           ========================= */

        emailOpt.ifPresent(email ->
                System.out.println("Email via ifPresent = " + email)
        );

        /* =========================
           4️⃣ orElse() vs orElseGet() vs orElseThrow()
           ========================= */

        String email1 =
                Optional.ofNullable(userWithoutEmail.getEmail())
                        .orElse("default@gmail.com"); // always evaluated

        String email2 =
                Optional.ofNullable(userWithoutEmail.getEmail())
                        .orElseGet(() -> getDefaultEmail()); // lazy

        String email3 =
                Optional.ofNullable(userWithEmail.getEmail())
                        .orElseThrow(() ->
                                new RuntimeException("Email not found")
                        );

        System.out.println(email1);
        System.out.println(email2);
        System.out.println(email3);

        /* =========================
           5️⃣ map()
           ========================= */

        Optional<String> upperEmail =
                Optional.ofNullable(userWithEmail.getEmail())
                        .map(String::toUpperCase);

        System.out.println("Upper Email = " + upperEmail.orElse("N/A"));

        /* =========================
           6️⃣ flatMap() (VERY IMPORTANT)
           ========================= */

        Optional<User> userOpt = Optional.of(userWithEmail);

        Optional<String> emailFlat =
                userOpt.flatMap(User::getEmailOptional);

        System.out.println("Email via flatMap = " + emailFlat.orElse("N/A"));

        /* =========================
           7️⃣ filter()
           ========================= */

        Optional<String> gmailOnly =
                Optional.ofNullable(userWithEmail.getEmail())
                        .filter(email -> email.endsWith("@gmail.com"));

        System.out.println("Gmail Email = " + gmailOnly.orElse("Not Gmail"));

        /* =========================
           8️⃣ or()
           ========================= */

        Optional<String> fallback =
                Optional.ofNullable(userWithoutEmail.getEmail())
                        .or(() -> Optional.of("fallback@gmail.com"));

        System.out.println("Fallback Email = " + fallback.get());

        /* =========================
           9️⃣ Optional in Stream
           ========================= */

        List<Optional<String>> emails =
                List.of(
                        Optional.of("a@gmail.com"),
                        Optional.empty(),
                        Optional.of("b@gmail.com")
                );

        emails.stream()
                .flatMap(Optional::stream)
                .forEach(System.out::println);

        /* =========================
           🔟 WRONG USAGE (INTERVIEW TRAPS)
           ========================= */

        // ❌ Never do this
        // Optional.of(null); // NullPointerException

        // ❌ Optional is NOT meant for fields
        // private Optional<String> email;

        // ❌ Don't call get() blindly
        // emailOpt.get();
    }

    private static String getDefaultEmail() {
        System.out.println("Computing default email...");
        return "lazy@gmail.com";
    }
}

/* =========================
   Helper Class
   ========================= */
class User {

    private String name;
    private String email;

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    // ✅ Best practice: return Optional from method
    public Optional<String> getEmailOptional() {
        return Optional.ofNullable(email);
    }
}