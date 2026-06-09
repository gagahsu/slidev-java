class BankAccount {
    String owner;
    double balance;

    void deposit(double amount) {
        balance += amount;
    }

    void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            System.out.println(owner + " 餘額不足，無法提款 " + amount);
        }
    }

    void displayInfo() {
        System.out.printf("%s 餘額：%.1f%n", owner, balance);
    }
}

public class BankAccountDemo {
    public static void main(String[] args) {
        BankAccount a1 = new BankAccount();
        a1.owner = "小明";
        a1.balance = 1000;
        a1.deposit(500);
        a1.withdraw(300);
        a1.displayInfo(); // 小明 餘額：1200.0

        BankAccount a2 = new BankAccount();
        a2.owner = "小華";
        a2.balance = 200;
        a2.deposit(100);
        a2.withdraw(500); // 餘額不足警告
        a2.displayInfo(); // 小華 餘額：300.0
    }
}
