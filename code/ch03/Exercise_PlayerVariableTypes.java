// Ch03 練習：變數分類與初始化
// 練習辨別三種變數（實例變數 / 類別變數 / 區域變數），以及區域變數沒有預設值的編譯錯誤

public class Exercise_PlayerVariableTypes {

    // 原始程式碼（無法編譯，僅供對照）：
    //
    // public class Player {
    //     int level = 1;            // 實例變數：宣告在類別內、方法外，沒有 static
    //     static int playerCount = 0; // 類別變數：有 static，所有物件共用
    //
    //     void levelUp() {
    //         int bonus;             // 區域變數：宣告在方法內
    //         level = level + 1;
    //         System.out.println(level + bonus); // 編譯錯誤：bonus 沒有預設值就直接使用
    //     }
    // }

    public static void main(String[] args) {
        Player player = new Player();
        player.levelUp();
        player.levelUp();
        System.out.println("目前等級：" + player.level);
        System.out.println("玩家總數：" + Player.playerCount);
    }
}

// 修正後的 Player 類別：補上 int bonus = 0; 才能編譯通過
class Player {
    int level = 1;              // 實例變數：每個物件各自一份
    static int playerCount = 0; // 類別變數：所有物件共用

    void levelUp() {
        int bonus = 0; // 區域變數：宣告後必須先賦值，才能使用
        level = level + 1;
        System.out.println(level + bonus);
    }
}
