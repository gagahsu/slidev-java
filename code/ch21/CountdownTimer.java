// 練習一：計時器執行緒
// 多執行緒倒數計時器，3 個計時器並行執行

public class CountdownTimer {

    // 實作 Runnable 介面的計時器任務
    static class CountdownTask implements Runnable {
        private final int seconds;
        private final String name;

        public CountdownTask(int seconds, String name) {
            this.seconds = seconds;
            this.name = name;
        }

        @Override
        public void run() {
            for (int i = seconds; i > 0; i--) {
                System.out.println("[" + name + "] 剩餘 " + i + " 秒");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            System.out.println("[" + name + "] 計時完成！");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 建立 3 個不同秒數的計時器
        Thread t1 = new Thread(new CountdownTask(3, "Timer-3"));
        Thread t2 = new Thread(new CountdownTask(5, "Timer-5"));
        Thread t3 = new Thread(new CountdownTask(7, "Timer-7"));

        // 全部 start() 後再 join()，才能並行執行
        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("所有計時器完成");

        // 進階挑戰：Lambda 版本（等效於上述寫法）
        System.out.println("\n--- Lambda 版本 ---");
        String[] names = {"Lambda-3", "Lambda-5", "Lambda-7"};
        int[] secs = {3, 5, 7};
        Thread[] threads = new Thread[3];

        for (int i = 0; i < 3; i++) {
            final int s = secs[i];
            final String n = names[i];
            threads[i] = new Thread(() -> {
                for (int j = s; j > 0; j--) {
                    System.out.println("[" + n + "] 剩餘 " + j + " 秒");
                    try { Thread.sleep(1000); } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); return;
                    }
                }
                System.out.println("[" + n + "] 計時完成！");
            });
        }
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();
        System.out.println("所有 Lambda 計時器完成");
    }
}
