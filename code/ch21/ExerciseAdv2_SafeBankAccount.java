// Ch21 練習二：安全的銀行帳戶
// 模擬多執行緒存提款，用 synchronized 方法保護共享餘額
// 驗證：去掉 synchronized 後結果不穩定（race condition），加回後結果一致

public class ExerciseAdv2_SafeBankAccount {

    // 使用不同於 ch21/BankAccount.java 的類別名稱，避免 default package 衝突
    static class SyncBankAccount {
        private int balance;

        public SyncBankAccount(int initialBalance) {
            this.balance = initialBalance;
        }

        // synchronized 方法：同一時間只有一個執行緒可以存款，避免 race condition
        public synchronized void deposit(int amount) {
            balance += amount;
            System.out.println(Thread.currentThread().getName()
                    + " 存款 " + amount + "，餘額：" + balance);
        }

        // 餘額不足時印出警告並回傳 false（不拋例外，符合業界一般慣例）
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
    }

    public static void main(String[] args) throws InterruptedException {
        // 初始餘額 1000，5 個存款執行緒（各存 200）+ 5 個提款執行緒（各提 300）
        SyncBankAccount account = new SyncBankAccount(1000);

        Thread[] threads = new Thread[10];
        for (int i = 0; i < 5; i++) {
            final int idx = i;
            // Lambda 本體使用 account（effectively final），不使用迴圈變數 i
            threads[idx]     = new Thread(() -> account.deposit(200),  "存款-" + (idx + 1));
            threads[idx + 5] = new Thread(() -> account.withdraw(300), "提款-" + (idx + 1));
        }

        // 先全部 start()，再全部 join()，確保並行執行
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();

        System.out.println("\n最終餘額：" + account.getBalance());
        // 理論：1000 + 5*200 = 2000，最多可提 6 次 300 = 1800，剩至少 200
        // synchronized 保證不會出現 race condition，結果每次執行都一致
    }
}
