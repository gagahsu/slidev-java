// 練習二：安全的銀行帳戶
// 使用 synchronized 確保多執行緒存提款的資料一致性

public class BankAccount {
    private int balance;

    public BankAccount(int initialBalance) {
        this.balance = initialBalance;
    }

    public synchronized void deposit(int amount) {
        balance += amount;
        System.out.println(Thread.currentThread().getName()
                + " 存款 " + amount + "，餘額：" + balance);
    }

    public synchronized boolean withdraw(int amount) {
        if (balance < amount) {
            System.out.println(Thread.currentThread().getName()
                    + " 提款 " + amount + " 失敗，餘額不足（目前：" + balance + "）");
            return false;
        }
        balance -= amount;
        System.out.println(Thread.currentThread().getName()
                + " 提款 " + amount + "，餘額：" + balance);
        return true;
    }

    public synchronized int getBalance() {
        return balance;
    }

    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccount(1000);

        // 5 個存款執行緒（各存 200）+ 5 個提款執行緒（各提 300）
        // Lambda 本體不使用 i，i 只用於執行緒名稱（立即求值），符合 effectively final 規則
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 5; i++) {
            threads[i]     = new Thread(() -> account.deposit(200),  "存款-" + (i + 1));
            threads[i + 5] = new Thread(() -> account.withdraw(300), "提款-" + (i + 1));
        }

        // 先全部 start
        for (Thread t : threads) t.start();
        // 再全部 join
        for (Thread t : threads) t.join();

        System.out.println("\n最終餘額：" + account.getBalance());
        // 理論：1000 + 5*200 = 2000，最多可提 6 次 300 = 1800，剩 200
        // 實際結果因執行順序不同而異，但 synchronized 保證不會出現 race condition
    }
}
