// Ch03 綜合練習：書籍訂單計算
// 綜合練習常數、不同型態變數、算術運算與 printf 格式化輸出

public class Exercise_BookOrder {
    static final double TAX_RATE = 0.05; // 稅率：static final 定義類別常數

    public static void main(String[] args) {
        String title = "Java 程式設計";
        double price = 300.0;
        int quantity = 5;

        double subtotal = price * quantity;
        double total = subtotal * (1 + TAX_RATE);

        System.out.printf("書名：%s%n", title);
        System.out.printf("小計：%.1f%n", subtotal);
        System.out.printf("總價：%.2f%n", total); // 證照常考：%.2f 取小數點後 2 位
    }
}
