// Ch15 練習 1：找出合約破綻
// 修正「只覆寫 equals() 而忘記覆寫 hashCode()」造成的 HashSet 找不到物件問題

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Exercise_UserContractFix {

    // 反例：只覆寫 equals()，忘記覆寫 hashCode()
    static class BrokenUser {
        String id;
        String email;

        BrokenUser(String id, String email) {
            this.id = id;
            this.email = email;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o instanceof BrokenUser u) {
                return Objects.equals(id, u.id) && Objects.equals(email, u.email);
            }
            return false;
        }
        // ❌ 故意不覆寫 hashCode，模擬合約破綻
    }

    // 修正版：hashCode() 使用與 equals() 相同的欄位 (id, email)
    static class User {
        String id;
        String email;

        User(String id, String email) {
            this.id = id;
            this.email = email;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o instanceof User u) {
                return Objects.equals(id, u.id) && Objects.equals(email, u.email);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, email); // 與 equals() 用同樣的欄位
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 反例：只覆寫 equals() ===");
        BrokenUser bu1 = new BrokenUser("u001", "alice@mail.com");
        BrokenUser bu2 = new BrokenUser("u001", "alice@mail.com");

        System.out.println("bu1.equals(bu2) = " + bu1.equals(bu2)); // true

        Set<BrokenUser> brokenSet = new HashSet<>();
        brokenSet.add(bu1);
        System.out.println("brokenSet.contains(bu2) = " + brokenSet.contains(bu2));
        // → false！equals 為 true，但 hashCode 不同，HashSet 找不到

        System.out.println("\n=== 修正版：補上 hashCode() ===");
        User u1 = new User("u001", "alice@mail.com");
        User u2 = new User("u001", "alice@mail.com");

        System.out.println("u1.equals(u2) = " + u1.equals(u2)); // true

        Set<User> set = new HashSet<>();
        set.add(u1);
        System.out.println("set.contains(u2) = " + set.contains(u2));
        // → true：equals 與 hashCode 用同樣的欄位，合約一致
    }
}
