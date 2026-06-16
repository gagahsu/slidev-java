// Ch16 練習：Account 抽象類別的建構方法與 Upcasting
// 抽象類別含建構方法與 protected 屬性，子類別呼叫 super(owner)，並用 Upcasting 宣告物件
// 注意：Account 已在 ch08/BankAccountDemo.java 的 default package 中被定義，
//       這裡改名為 AbstractAccount 以避免 Eclipse 跨 source folder 的 default package 衝突。

abstract class AbstractAccount {
    protected String owner;

    AbstractAccount(String owner) {
        this.owner = owner;
        System.out.println("開戶完成");
    }

    abstract void showType();
}

class SavingsAccount extends AbstractAccount {
    SavingsAccount(String owner) {
        super(owner); // 子類別建構方法第一行呼叫 super
    }

    @Override
    void showType() {
        System.out.println(owner + " 的帳戶類型：活存");
    }
}

public class Exercise_AccountUpcasting {
    public static void main(String[] args) {
        // Upcasting：AbstractAccount 雖不能 new，但可以當作變數宣告型態
        AbstractAccount acc = new SavingsAccount("古古"); // 先印出「開戶完成」
        acc.showType(); // 古古 的帳戶類型：活存
    }
}
