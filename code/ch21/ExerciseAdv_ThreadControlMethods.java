// Ch21 練習：執行緒控制方法辨析（認證模擬題）
// 觀察 setDaemon() 與 sleep() 對「JVM 何時退出」的影響
//
// 證照／面試常考重點：
// 1. setDaemon(true) 必須在 start() 之前呼叫，否則丟出 IllegalThreadStateException
// 2. JVM 的退出條件只看 User 執行緒：main（唯一的 User 執行緒）一旦結束，
//    所有 Daemon 執行緒會被「強制終止」，不會等它做完手上的工作
// 3. sleep() 不會釋放任何鎖，純粹只是讓出時間片

public class ExerciseAdv_ThreadControlMethods {
    public static void main(String[] args) throws InterruptedException {
        Thread worker = new Thread(() -> {
            try {
                // 為了讓 demo 能在合理時間內跑完，把 sleep 時間調短（原題為 2000ms）
                Thread.sleep(500);
            } catch (InterruptedException e) {}
            System.out.println("worker 完成");
        });

        // setDaemon(true) 必須在 start() 之前呼叫
        worker.setDaemon(true);
        worker.start();

        System.out.println("main 結束");

        // ---- 觀察一：main 結束後立即退出（不等待 worker） ----
        // 上面這段執行完，main 執行緒就結束了。
        // 因為 worker 是 Daemon 執行緒，且此時已無其他 User 執行緒，
        // JVM 會直接強制終止 worker，"worker 完成" 很可能根本來不及印出。

        // ---- 觀察二：若改用 join()，main 會等待 worker 結束 ----
        // 為了讓兩種情境都能在同一支程式觀察到，這裡額外示範一次「等待版」：
        Thread worker2 = new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {}
            System.out.println("worker2 完成");
        });
        worker2.setDaemon(true);
        worker2.start();
        worker2.join();  // 等待 worker2 跑完，這次保證會印出 "worker2 完成"
        System.out.println("main 真正結束（join 之後）");

        // 認證模擬題答案：C
        // A. ❌ setDaemon(true) 必須在 start() 之前呼叫才有效（此程式呼叫順序正確）
        // B. ❌ "main 結束" 通常會先印出，但「一定」這個說法不準確（排程順序不保證）
        // C. ✅ main 結束後，Daemon 執行緒可能被直接強制終止，"worker 完成" 不一定會印出
        // D. ❌ sleep() 不會釋放任何鎖
    }
}
