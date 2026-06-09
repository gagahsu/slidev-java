import java.util.Objects;

/**
 * 練習：Employee 類別
 * 1. 建立兩個屬性值相同的 Employee 物件，比較 hashCode()
 * 2. 觀察 Object 預設 hashCode()（不同物件即使屬性相同，hashCode 也不同）
 * 3. 進階：Override hashCode() 使屬性相同的物件回傳相同 hash 值
 *
 * 注意：Override hashCode() 時也應一併 Override equals()，
 *       否則 HashMap 等集合類別行為會不一致。
 */
public class EmployeeHashCode {

    // ── 步驟一：預設版（不 Override），觀察行為 ──
    static class EmployeeDefault {
        String name;
        int age;
        String country;

        EmployeeDefault(String name, int age, String country) {
            this.name = name;
            this.age = age;
            this.country = country;
        }
    }

    // ── 步驟二：進階版（Override hashCode 與 equals）──
    static class Employee {
        String name;
        int age;
        String country;

        Employee(String name, int age, String country) {
            this.name = name;
            this.age = age;
            this.country = country;
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, age, country);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Employee other)) return false;
            return age == other.age
                && Objects.equals(name, other.name)
                && Objects.equals(country, other.country);
        }

        @Override
        public String toString() {
            return "Employee{name='%s', age=%d, country='%s'}".formatted(name, age, country);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 步驟一：預設 hashCode（未 Override）===");
        EmployeeDefault e1 = new EmployeeDefault("Alice", 30, "Taiwan");
        EmployeeDefault e2 = new EmployeeDefault("Alice", 30, "Taiwan");
        System.out.println("e1.hashCode() = " + e1.hashCode());
        System.out.println("e2.hashCode() = " + e2.hashCode());
        System.out.println("兩個 hashCode 相同嗎？" + (e1.hashCode() == e2.hashCode()));
        // → false：Object 預設 hashCode 基於記憶體位址，不同物件結果不同

        System.out.println("\n=== 步驟二：Override hashCode（屬性相同 → 相同 hash）===");
        Employee emp1 = new Employee("Alice", 30, "Taiwan");
        Employee emp2 = new Employee("Alice", 30, "Taiwan");
        System.out.println("emp1.hashCode() = " + emp1.hashCode());
        System.out.println("emp2.hashCode() = " + emp2.hashCode());
        System.out.println("兩個 hashCode 相同嗎？" + (emp1.hashCode() == emp2.hashCode()));
        // → true

        System.out.println("\n=== equals() 驗證 ===");
        System.out.println("emp1.equals(emp2) = " + emp1.equals(emp2));
        // → true（因為屬性相同）

        Employee emp3 = new Employee("Bob", 25, "Japan");
        System.out.println("emp1.equals(emp3) = " + emp1.equals(emp3));
        // → false（屬性不同）
    }
}
