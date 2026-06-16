// Ch09 進階練習：Lazy 與 Eager 初始化
// 認證模擬題：判斷兩種 Singleton 寫法分別屬於 Lazy 還是 Eager 初始化
// 業界選擇原則：物件輕且一定用到 → Eager 簡單安全；建立成本高且不一定用到 → Lazy

// 寫法一：Eager 初始化（餓漢式）— 類別載入時立即建立，天生執行緒安全
class ConfigEager {
    // 欄位宣告時直接 new，類別載入即完成初始化
    private static final ConfigEager instance = new ConfigEager();
    private ConfigEager() {}
    public static ConfigEager getInstance() { return instance; }
}

// 寫法二：Lazy 初始化（懶漢式）— 第一次呼叫 getInstance() 才建立
class ConfigLazy {
    private static ConfigLazy instance = null;
    private ConfigLazy() {}
    public static ConfigLazy getInstance() {
        // 證照常考：Lazy 以 instance == null 判斷是否已建立
        if (instance == null) instance = new ConfigLazy();
        return instance;
    }
}

public class ExerciseAdv2_LazySingleton {
    public static void main(String[] args) {
        // Eager：不管有沒有呼叫 getInstance()，類別載入就已建立
        ConfigEager e1 = ConfigEager.getInstance();
        ConfigEager e2 = ConfigEager.getInstance();
        System.out.println("Eager e1 == e2：" + (e1 == e2)); // true

        // Lazy：第一次呼叫才建立，之後都回傳同一份
        ConfigLazy l1 = ConfigLazy.getInstance();
        ConfigLazy l2 = ConfigLazy.getInstance();
        System.out.println("Lazy  l1 == l2：" + (l1 == l2)); // true

        // 正確答案（模擬題）：C
        // 寫法一（ConfigEager）= Eager 初始化，類別載入就建立
        // 寫法二（ConfigLazy） = Lazy 初始化，第一次呼叫才建立
    }
}
