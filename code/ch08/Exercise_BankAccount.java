// Ch08 綜合練習一：設計 BankAccount 類別
// 練習設計含欄位與方法的完整類別，並加入防呆邏輯（餘額不足時拒絕提款）
// 注意：類別命名為 BankAcct，避免與同目錄 BankAccountDemo.java 中的 Account 撞名
// （Eclipse default package 下所有 top-level class 名稱必須全域唯一）

class BankAcct {
    String owner;   // 戶主姓名
    double balance; // 餘額

    // 存款：餘額增加
    void deposit(double amount) {
        balance += amount;
    }

    // 提款：餘額不足時印出警告，拒絕扣款
    void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            System.out.println(owner + " 餘額不足，無法提款 " + amount);
        }
    }

    // 印出戶主與餘額
    void displayInfo() {
        System.out.printf("%s 餘額：%.1f%n", owner, balance);
    }
}

public class Exercise_BankAccount {
    public static void main(String[] args) {
        BankAcct a1 = new BankAcct();
        a1.owner = "小明";
        a1.balance = 1000;
        a1.deposit(500);    // 1000 + 500 = 1500
        a1.withdraw(300);   // 1500 - 300 = 1200
        a1.displayInfo();   // 小明 餘額：1200.0

        BankAcct a2 = new BankAcct();
        a2.owner = "小華";
        a2.balance = 200;
        a2.deposit(100);    // 200 + 100 = 300
        a2.withdraw(500);   // 餘額不足，印出警告
        a2.displayInfo();   // 小華 餘額：300.0
    }
}
