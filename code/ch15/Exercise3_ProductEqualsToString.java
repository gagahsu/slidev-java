// Ch15 練習：equals() 與 toString() 的對齊
// 練習覆寫 toString() 與 equals()，equals() 只比較 code 是否相同

import java.util.Objects;

public class Exercise3_ProductEqualsToString {

    static class Product {
        String code;
        String name;
        double price;

        Product(String code, String name, double price) {
            this.code = code;
            this.name = name;
            this.price = price;
        }

        @Override
        public String toString() {
            return "Code: " + code + ", Name: " + name + ", Price: " + price;
        }

        // 此處只用 code 判斷相等，代表「商品代碼」就是 Product 的識別依據
        // 注意：若要放進 HashSet/HashMap，hashCode() 也應只用 code，與 equals() 保持一致
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o instanceof Product other) {
                return Objects.equals(this.code, other.code);
            }
            return false;
        }
    }

    public static void main(String[] args) {
        Product p1 = new Product("P001", "滑鼠", 299.0);
        Product p2 = new Product("P001", "鍵盤", 999.0); // code 相同，name/price 不同

        System.out.println(p1);
        System.out.println(p2);

        System.out.println("p1.equals(p2) = " + p1.equals(p2));
        // → true：equals() 只比較 code，code 相同即視為同一商品
    }
}
