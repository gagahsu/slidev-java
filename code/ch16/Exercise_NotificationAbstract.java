// Ch16 練習：設計 Notification 抽象類別
// 抽象類別 Notification 含「空實作」方法 send()，子類別各自 override 不同的通知方式

abstract class Notification {
    public void send() { } // 空實作骨架
}

class EmailNotification extends Notification {
    @Override
    public void send() {
        System.out.println("以 Email 發送通知");
    }
}

class SmsNotification extends Notification {
    @Override
    public void send() {
        System.out.println("以 SMS 發送通知");
    }
}

public class Exercise_NotificationAbstract {
    public static void main(String[] args) {
        Notification n1 = new EmailNotification();
        Notification n2 = new SmsNotification();
        n1.send(); // 以 Email 發送通知
        n2.send(); // 以 SMS 發送通知

        // Notification n3 = new Notification(); // 編譯錯誤！
        // 證照常考：'Notification' is abstract; cannot be instantiated
    }
}
