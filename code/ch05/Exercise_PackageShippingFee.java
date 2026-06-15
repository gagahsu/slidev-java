// Ch05 綜合練習：包裹運費試算器
// 結合 sealed interface、record 與 instanceof Pattern Matching 計算運費
// 註：switch 的 Pattern Matching 在 JDK 17 仍是預覽功能，要到 JDK 21 才正式定案，
//     這裡改寫成 if/else + instanceof，JDK 17 可直接編譯

public class Exercise_PackageShippingFee {

    sealed interface Package permits Document, Box {}

    record Document(double weight) implements Package {}

    record Box(double weight, boolean fragile) implements Package {}

    static int calcFee(Package p) {
        // 易碎判斷要寫在前面，否則一般 Box 的條件會先比對成功
        if (p instanceof Document d) {
            return (int) Math.round(d.weight() * 20);
        } else if (p instanceof Box b && b.fragile()) {
            return (int) Math.round(b.weight() * 50);
        } else if (p instanceof Box b) {
            return (int) Math.round(b.weight() * 35);
        }
        // 證照常考：Document、Box 是 sealed interface 的全部子型別，
        // 邏輯上窮舉後不會走到這裡，但 if/else 寫法仍需要明確的回傳/拋出
        throw new IllegalStateException("未知的 Package 型態");
    }

    public static void main(String[] args) {
        Package box = new Box(3.0, true);
        System.out.println("運費：" + calcFee(box) + " 元"); // 運費：150 元

        Package doc = new Document(2.0);
        System.out.println("運費：" + calcFee(doc) + " 元"); // 運費：40 元

        Package normalBox = new Box(3.0, false);
        System.out.println("運費：" + calcFee(normalBox) + " 元"); // 運費：105 元
    }
}
