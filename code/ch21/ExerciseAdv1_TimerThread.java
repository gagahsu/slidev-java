// Ch21 練習一：計時器執行緒
// 設計多執行緒倒數計時器：CountdownTask 實作 Runnable，同時啟動 3 個不同秒數的計時器
// 使用 join() 等待所有計時器完成後印出「所有計時器完成」

public class ExerciseAdv1_TimerThread {

    // 實作 Runnable 介面的計時器任務（業界推薦：職責分離，不繼承 Thread）
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
                    Thread.sleep(1000); // 每秒倒數一次
                } catch (InterruptedException e) {
                    // 業界實務：InterruptedException 要恢復中斷旗標，不能吞掉
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            System.out.println("[" + name + "] 計時完成！");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 建立 3 個不同秒數的計時器執行緒
        Thread t1 = new Thread(new CountdownTask(3, "Timer-3"));
        Thread t2 = new Thread(new CountdownTask(5, "Timer-5"));
        Thread t3 = new Thread(new CountdownTask(7, "Timer-7"));

        // 全部 start() 後再 join()，才能真正並行執行
        // 注意：如果 start() 後立刻 join()，就變成依序等待，失去並行效果
        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("所有計時器完成");

        // 進階挑戰：Lambda 版本（功能等效）
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
