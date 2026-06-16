// Ch21 練習：執行緒基礎概念
// 認證模擬題（單選）
// 關於 Thread 的狀態與 Program / Process / Thread 三者的關係

// 正確答案：B
// A. ❌ 一個 Process 內可以有「多個」Thread，多個執行緒共用同一個 Process 的資源
// B. ✅ new Thread() 之後、尚未呼叫 start() 之前，狀態是 NEW
// C. ❌ sleep() 觸發的是 TIMED_WAITING；BLOCKED 是「競爭 synchronized 鎖失敗」才會進入
// D. ❌ TERMINATED 執行緒無法再被 start()，重新呼叫會丟出 IllegalThreadStateException

public class ExerciseAdv_ThreadBasicConcept {

    public static void main(String[] args) throws InterruptedException {
        // 示範各種執行緒狀態

        // ① NEW 狀態：Thread 物件已建立，尚未呼叫 start()
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(200); // TIMED_WAITING（不是 BLOCKED）
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        System.out.println("建立後、start() 前：" + t.getState()); // NEW

        // ② RUNNABLE 狀態：呼叫 start() 後
        t.start();
        System.out.println("start() 後：" + t.getState()); // RUNNABLE 或 TIMED_WAITING

        // ③ TIMED_WAITING：執行緒在 sleep() 期間（注意不是 BLOCKED）
        Thread.sleep(50); // 讓 t 有機會進入 sleep
        System.out.println("sleep() 期間：" + t.getState()); // TIMED_WAITING

        // ④ TERMINATED：執行緒結束後
        t.join();
        System.out.println("結束後：" + t.getState()); // TERMINATED

        // 業界／證照常考：
        // - BLOCKED   = 搶 synchronized 鎖搶輸了（被動等鎖）
        // - WAITING   = 呼叫 wait() / join()（主動無限等待，需要 notify/notifyAll 喚醒）
        // - TIMED_WAITING = 呼叫 sleep(ms) / wait(ms) / join(ms)（有時限的等待）
        System.out.println("\n記住：sleep() → TIMED_WAITING，搶鎖失敗 → BLOCKED，wait() → WAITING");
    }
}
