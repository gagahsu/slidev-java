// Ch21 練習：售票系統的同步區塊
// 模擬演唱會售票系統，用「同步區塊（synchronized block）」保護共享資源
// 重點觀察：最終 remainingTickets 不會出現負數或重複賣出同一張票

public class ExerciseAdv_TicketBoothSync {

    static class TicketBooth {
        private int remainingTickets = 100;
        private final Object lock = new Object();

        public void sellTicket(String buyerName) {
            // 準備工作：只是印出訊息，不需要加鎖，多個執行緒可同時印出
            System.out.println(buyerName + " 嘗試購票...");

            // 只把「檢查剩餘票數 + 扣減」這一段鎖起來（臨界區越小越好）
            synchronized (lock) {
                if (remainingTickets > 0) {
                    remainingTickets--;
                    System.out.println(buyerName + " 購票成功，剩餘 "
                            + remainingTickets + " 張");
                } else {
                    System.out.println(buyerName + " 購票失敗，已售完");
                }
            }
        }

        public int getRemainingTickets() {
            synchronized (lock) {
                return remainingTickets;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 為了讓 demo 較快看到「售完」的情況，這裡把票數情境縮小：
        // 用 10 位買家搶 100 張票（不會售完），可改小 remainingTickets 觀察售完情況
        TicketBooth booth = new TicketBooth();

        Thread[] buyers = new Thread[10];
        for (int i = 1; i <= 10; i++) {
            final int id = i;
            buyers[i - 1] = new Thread(() -> booth.sellTicket("買家" + id));
        }

        for (Thread t : buyers) t.start();
        for (Thread t : buyers) t.join();

        // 業界常考：同步區塊 vs 同步方法
        // - 把整個 sellTicket 都加 synchronized：寫法簡單，但「準備工作」也會被鎖住，效能較差
        // - 只鎖「檢查 + 扣票」這一小段：臨界區變小，其他執行緒不需排隊等待印訊息
        // 兩種寫法都能避免超賣（race condition），差別只在效能
        System.out.println("\n最終剩餘票數：" + booth.getRemainingTickets());
        // 理論：100 張票，10 人買，最終應剩 90 張，且每張票只會被賣出一次
    }
}
