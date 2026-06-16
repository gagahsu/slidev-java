// Ch21 練習三：生產者消費者佇列
// 實作有界緩衝區（Bounded Buffer）的生產者消費者模式
// 使用 wait() / notifyAll() 在佇列滿或空時協調執行緒

import java.util.LinkedList;

public class ExerciseAdv3_ProducerConsumerQueue {

    // 有界緩衝區，最大容量 5
    // 使用不同於 ch21/ProducerConsumer.java 的類別名稱，避免 default package 衝突
    static class BoundedBuffer {
        private final LinkedList<Integer> queue = new LinkedList<>();
        private final int capacity;

        public BoundedBuffer(int capacity) {
            this.capacity = capacity;
        }

        // 佇列滿時 wait()；否則加入並 notifyAll()
        // 證照常考：wait() 必須在 synchronized 區塊內呼叫，且一定要用 while 包住（防止虛假喚醒）
        public synchronized void put(int item) throws InterruptedException {
            while (queue.size() == capacity) {
                wait(); // 佇列滿，生產者等待
            }
            queue.addLast(item);
            System.out.println(Thread.currentThread().getName()
                    + " 放入 " + item + "，佇列大小：" + queue.size());
            notifyAll(); // 喚醒所有等待的執行緒（消費者或生產者）
        }

        // 佇列空時 wait()；否則取出並 notifyAll()
        public synchronized int take() throws InterruptedException {
            while (queue.isEmpty()) {
                wait(); // 佇列空，消費者等待
            }
            int val = queue.removeFirst();
            System.out.println(Thread.currentThread().getName()
                    + " 取出 " + val + "，佇列大小：" + queue.size());
            notifyAll(); // 喚醒所有等待的執行緒
            return val;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BoundedBuffer buffer = new BoundedBuffer(5);
        int producerCount = 2;
        int consumerCount = 3;
        int itemsPerProducer = 10; // 每個生產者生產 10 個數字，共 20 個

        // 建立消費者執行緒：取到 -1 結束訊號時停止
        Thread[] consumers = new Thread[consumerCount];
        for (int i = 0; i < consumerCount; i++) {
            final int cid = i + 1;
            consumers[i] = new Thread(() -> {
                try {
                    while (true) {
                        int val = buffer.take();
                        if (val == -1) break; // 收到結束訊號，停止消費
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "消費者-" + cid);
        }

        // 建立生產者執行緒：各生產 10 個整數
        Thread[] producers = new Thread[producerCount];
        for (int i = 0; i < producerCount; i++) {
            final int pid = i + 1;
            producers[i] = new Thread(() -> {
                try {
                    for (int j = 1; j <= itemsPerProducer; j++) {
                        buffer.put(pid * 100 + j); // 生產者 1 放 101~110，生產者 2 放 201~210
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "生產者-" + pid);
        }

        // 先啟動消費者，再啟動生產者（避免生產者先跑完、消費者還沒就緒）
        for (Thread c : consumers) c.start();
        for (Thread p : producers) p.start();

        // 等生產者全部完成後，送出結束訊號（每個消費者一個 -1）
        for (Thread p : producers) p.join();
        for (int i = 0; i < consumerCount; i++) {
            buffer.put(-1); // 業界常用「毒丸（Poison Pill）」模式通知消費者停止
        }

        // 等所有消費者完成
        for (Thread c : consumers) c.join();
        System.out.println("所有生產者與消費者完成");

        // 重點：
        // - notifyAll() 比 notify() 安全：notify() 可能喚醒另一個消費者而非生產者，
        //   導致所有執行緒卡在 wait()（隱性死結）
        // - wait() 要用 while 包住：防止「虛假喚醒（spurious wakeup）」
    }
}
