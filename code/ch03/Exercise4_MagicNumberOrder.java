// Ch03 練習：消除神秘數字
// 把程式中的神秘數字（99.5、1000、0.9）改成 static final 常數，提升可讀性與維護性

public class Exercise4_MagicNumberOrder {
    static final double UNIT_PRICE = 99.5;          // 單價
    static final double DISCOUNT_THRESHOLD = 1000;  // 折扣門檻
    static final double DISCOUNT_RATE = 0.9;        // 折扣係數

    public static void main(String[] args) {
        int quantity = 5;
        double total = quantity * UNIT_PRICE;

        if (total > DISCOUNT_THRESHOLD) {
            total = total * DISCOUNT_RATE;
        }
        System.out.println(total);
    }
}
