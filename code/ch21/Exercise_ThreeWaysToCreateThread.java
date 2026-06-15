// Ch21 練習：三種方式建立執行緒
// 用「繼承 Thread」「實作 Runnable」「Lambda」三種寫法，
// 各自建立一個執行緒，完成同一件事：印出 5 次 "訊息 #N"

// ① 繼承 Thread 類別
class MessageThread extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("[MessageThread] 訊息 #" + i);
        }
    }
}

// ② 實作 Runnable 介面（業界推薦：任務與執行緒職責分離，不佔用唯一的繼承機會）
class MessagePrinter implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("[MessagePrinter] 訊息 #" + i);
        }
    }
}

public class Exercise_ThreeWaysToCreateThread {
    public static void main(String[] args) throws InterruptedException {
        // ① 繼承 Thread：直接 start()
        Thread t1 = new MessageThread();

        // ② 實作 Runnable：包裝成 Thread 再 start()
        Thread t2 = new Thread(new MessagePrinter());

        // ③ Lambda（Java 8+）：Runnable 是 @FunctionalInterface，可直接用 Lambda 取代整個類別
        Thread t3 = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("[Lambda] 訊息 #" + i);
            }
        });

        // 注意：呼叫的是 start()，不是 run()！直接呼叫 run() 不會建立新執行緒
        t1.start();
        t2.start();
        t3.start();

        // join() 等待三個執行緒都跑完，避免 main 提前結束
        t1.join();
        t2.join();
        t3.join();

        System.out.println("三種方式建立的執行緒皆已完成");
        // 三個執行緒的輸出順序可能交錯出現，這是正常的並行現象
    }
}
