// Ch09 練習：設計 AppLogger 單例類別
// 練習 Singleton 設計模式的三要素：private 建構子、private static 欄位、public static getInstance()

class AppLogger {
    private static AppLogger instance = null;

    private AppLogger() {}

    // Lazy 初始化：第一次呼叫才真正建立物件
    public static AppLogger getInstance() {
        if (instance == null) {
            instance = new AppLogger();
        }
        return instance;
    }

    public void log(String message) {
        System.out.println("[LOG] " + message);
    }
}

public class ExerciseAdv1_AppLoggerSingleton {
    public static void main(String[] args) {
        AppLogger logger1 = AppLogger.getInstance();
        AppLogger logger2 = AppLogger.getInstance();

        // 兩次取得的是同一個物件，比較記憶體位址會是 true
        System.out.println(logger1 == logger2); // true
        logger1.log("系統啟動");
    }
}
