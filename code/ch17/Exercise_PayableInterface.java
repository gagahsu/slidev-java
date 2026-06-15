// Ch17 練習：實作多種付款方式
// 介面 Payable 定義 pay(int amount)，CreditCard 與 CashPayment 各自實作不同付款方式

interface Payable {
    void pay(int amount);
}

class CreditCard implements Payable {
    @Override
    public void pay(int amount) {
        System.out.println("信用卡付款 " + amount + " 元");
    }
}

class CashPayment implements Payable {
    @Override
    public void pay(int amount) {
        System.out.println("現金付款 " + amount + " 元");
    }
}

public class Exercise_PayableInterface {
    public static void main(String[] args) {
        // 業界常用寫法：用介面型態宣告變數，呼叫端不需要知道實際是哪個實作
        Payable p1 = new CreditCard();
        Payable p2 = new CashPayment();

        p1.pay(500); // 信用卡付款 500 元
        p2.pay(500); // 現金付款 500 元
    }
}
