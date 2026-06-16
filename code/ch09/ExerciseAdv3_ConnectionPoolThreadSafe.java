// Ch09 練習：為 Lazy Singleton 加上執行緒安全
// 在 getInstance() 加上 synchronized，避免多執行緒下同時通過 instance == null 判斷而建立出多個實體

class ConnectionPool {
    private static ConnectionPool instance = null;

    private ConnectionPool() {}

    // 加上 synchronized：同一時間只允許一個 thread 執行此方法
    public static synchronized ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }
}

public class ExerciseAdv3_ConnectionPoolThreadSafe {
    public static void main(String[] args) {
        ConnectionPool c1 = ConnectionPool.getInstance();
        ConnectionPool c2 = ConnectionPool.getInstance();

        // 即使在多執行緒環境下，synchronized 也能保證只建立一個實體
        System.out.println("c1 == c2：" + (c1 == c2)); // true
    }
}
