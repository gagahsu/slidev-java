// Ch16 練習：用抽象方法強制實作 pay()
// 抽象方法強制子類別 override；OnlinePayment 暫不實作 pay()，改用「延遲實作」(也宣告為 abstract)

abstract class Payment {
    abstract void pay(int amount);
}

class CreditCardPayment extends Payment {
    @Override
    void pay(int amount) {
        System.out.println("信用卡支付 " + amount + " 元");
    }
}

// 解法二：延遲實作，類別也宣告為 abstract，pay() 留給 OnlinePayment 的子類別實作
// 證照常考：若拿掉 abstract 又不實作 pay()，會出現
// "Class 'OnlinePayment' must implement abstract method 'pay(int)' in 'Payment'"
abstract class OnlinePayment extends Payment {
}

public class Exercise_PaymentAbstract {
    public static void main(String[] args) {
        Payment p = new CreditCardPayment();
        p.pay(1000); // 信用卡支付 1000 元
    }
}
