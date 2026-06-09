// 練習三：生產者消費者佇列
// 使用 wait/notifyAll 實作有界緩衝區（Bounded Buffer）

import java.util.LinkedList;

public class ProducerConsumer {

    // 有界緩衝區，最大容量 5
    static class BoundedQueue {
        private final LinkedList<Integer> queue = new LinkedList<>();
        private final int capacity;

        public BoundedQueue(int capacity) {
            this.capacity = capacity;
        }

        // 佇列滿時 wait()，否則加入並 notifyAll()
        public synchronized void put(int item) throws InterruptedException {
            while (queue.size() == capacity) {
                wait();
            }
            queue.addLast(item);
            System.out.println(Thread.currentThread().getName()
                    + " 放入 " + item + "，佇列大小：" + queue.size());
            notifyAll();  // 喚醒等待的消費者（或生產者）
        }

        // 佇列空時 wait()，否則取出並 notifyAll()
        public synchronized int take() throws InterruptedException {
            while (queue.isEmpty()) {
                wait();
            }
            int val = queue.removeFirst();
            System.out.println(Thread.currentThread().getName()
                    + " 取出 " + val + "，佇列大小：" + queue.size());
            notifyAll();  // 喚醒等待的生產者（或消費者）
            return val;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BoundedQueue bq = new BoundedQueue(5);
        int producerCount = 2;
        int consumerCount = 3;
        int itemsPerProducer = 10;
        // 總共生產 2*10=20 個數字，結束訊號：每個消費者收到 -1 就停止
        // 生產者結束後傳送 consumerCount 個 -1 作為結束訊號

        // 建立消費者執行緒
        Thread[] consumers = new Thread[consumerCount];
        for (int i = 0; i < consumerCount; i++) {
            consumers[i] = new Thread(() -> {
                try {
                    while (true) {
                        int val = bq.take();
                        if (val == -1) break;  // 收到結束訊號，停止
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "消費者-" + (i + 1));
        }

        // 建立生產者執行緒
        Thread[] producers = new Thread[producerCount];
        for (int i = 0; i < producerCount; i++) {
            final int pid = i + 1;
            producers[i] = new Thread(() -> {
                try {
                    for (int j = 1; j <= itemsPerProducer; j++) {
                        bq.put(pid * 100 + j);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "生產者-" + pid);
        }

        // 先啟動消費者，再啟動生產者
        for (Thread c : consumers) c.start();
        for (Thread p : producers) p.start();

        // 等生產者完成後，送出結束訊號（每個消費者一個 -1）
        for (Thread p : producers) p.join();
        for (int i = 0; i < consumerCount; i++) {
            bq.put(-1);
        }

        // 等所有消費者完成
        for (Thread c : consumers) c.join();
        System.out.println("所有生產者與消費者完成");
    }
}
