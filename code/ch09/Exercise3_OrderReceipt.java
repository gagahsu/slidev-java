// Ch09 練習3：訂單小幫手（延伸）
// 練習方法拆分：驗證、計算、輸出各自獨立，main() 只負責依序呼叫

public class Exercise3_OrderReceipt {

    public static double calculateSubtotal(int price, int qty) {
        return price * qty;
    }

    public static double applyDiscount(double subtotal, boolean isMember) {
        if (isMember) {
            return subtotal * 0.9;
        }
        return subtotal;
    }

    public static void printReceipt(double finalAmount) {
        System.out.println("應付金額：" + finalAmount);
    }

    public static void main(String[] args) {
        double subtotal = calculateSubtotal(120, 3);
        double total = applyDiscount(subtotal, true);
        printReceipt(total); // 應付金額：324.0
    }
}
