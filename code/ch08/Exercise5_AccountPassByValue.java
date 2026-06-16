// Ch08 練習：Pass by Value 與多載驗證
// 練習用 this 區分建構子參數與欄位、方法多載（overloading），
// 並驗證「物件參照重新賦值不會影響外部物件」這個 pass-by-value 的重點

// 命名為 BonusAccount，避免與 BankAccountDemo.java 中的 Account 類別撞名
// （同一個 default package 下，所有 top-level class 名稱必須唯一）
class BonusAccount {
    String owner;
    int balance;

    BonusAccount(String owner, int balance) {
        this.owner = owner;
        this.balance = balance;
    }

    // 多載：依參數數量自動選擇對應版本
    void addBonus(int amount) {
        balance += amount;
    }

    void addBonus(int amount, double rate) {
        balance += (int) (amount * rate); // 取整數部分
    }

    // static 方法：acc 只是參數的「複本參照」，重新賦值不會影響呼叫端的物件
    static void tryReset(BonusAccount acc) {
        acc = new BonusAccount("空帳戶", 0); // 只改了方法內的參照，呼叫端的物件不受影響
    }
}

public class Exercise5_AccountPassByValue {
    public static void main(String[] args) {
        BonusAccount a = new BonusAccount("小明", 1000);

        a.addBonus(100);          // balance = 1100
        a.addBonus(100, 0.5);     // balance = 1100 + 50 = 1150

        BonusAccount.tryReset(a);
        System.out.println(a.balance); // 仍是 1150，未被 tryReset 影響
    }
}
