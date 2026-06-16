// Ch17-adv 練習：自訂功能介面與工具方法
// @FunctionalInterface 結合 default 方法（printPrice）與 static 方法（noDiscount）

@FunctionalInterface
interface Discountable {
    double discount(double price); // 唯一抽象方法，可用 Lambda 實作

    default void printPrice(double price) {
        System.out.println("優惠後價格：" + discount(price));
    }

    // 業界常用寫法：static 方法當工具箱，提供常用的預設實作
    static Discountable noDiscount() {
        return price -> price; // 原價不變
    }
}

public class ExerciseAdv_DiscountableFunctional {
    public static void main(String[] args) {
        // 打 8 折
        Discountable eightyPercent = price -> price * 0.8;
        eightyPercent.printPrice(1000); // 優惠後價格：800.0

        // 不打折
        Discountable.noDiscount().printPrice(1000); // 優惠後價格：1000.0
    }
}
