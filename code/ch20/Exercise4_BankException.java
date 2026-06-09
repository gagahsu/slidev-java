// Ch20 練習4：自訂異常類別
// 銀行存提款程式：提款金額大於存款時拋出 NotEnoughException，傳入差額

class NotEnoughException extends Exception {
    private int shortAmount;

    NotEnoughException(int shortAmount) {
        super("餘額不足");
        this.shortAmount = shortAmount;
    }

    public int getShortAmount() {
        return shortAmount;
    }
}

class MyBank {
    private int balance;

    MyBank(int initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(int amount) {
        balance += amount;
        System.out.println("存款 " + amount + " 元，目前餘額：" + balance);
    }

    public void withdraw(int cashout) throws NotEnoughException {
        if (cashout > balance) {
            int shortAmount = cashout - balance;
            throw new NotEnoughException(shortAmount);
        }
        balance -= cashout;
        System.out.println("提款 " + cashout + " 元成功，目前餘額：" + balance);
    }
}

public class Exercise4_BankException {
    public static void main(String[] args) {
        MyBank bank = new MyBank(1000);
        bank.deposit(500);  // 存款 500，餘額 1500

        try {
            bank.withdraw(800);   // 成功提款
        } catch (NotEnoughException e) {
            System.out.println("提款失敗：" + e.getMessage() + "，還差 " + e.getShortAmount() + " 元");
        }

        try {
            bank.withdraw(1000);  // 超額提款
        } catch (NotEnoughException e) {
            System.out.println("提款失敗：" + e.getMessage() + "，還差 " + e.getShortAmount() + " 元");
        }
    }
}
