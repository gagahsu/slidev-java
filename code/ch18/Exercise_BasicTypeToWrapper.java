// Ch18 練習：基本型態與包裝類別轉換
// 宣告 int / boolean 變數，再宣告對應的包裝類別變數並指派，印出值與型態名稱

public class Exercise_BasicTypeToWrapper {
    public static void main(String[] args) {
        int level = 5;
        boolean isOnline = true;

        // 自動裝箱 (Autoboxing)：基本型態的值直接指派給包裝類別變數
        Integer levelObj = level;
        Boolean isOnlineObj = isOnline;

        // 基本型態沒有 getClass()，型態名稱直接以字面說明
        System.out.println("level = " + level + "，型態：int");
        System.out.println("isOnline = " + isOnline + "，型態：boolean");

        // 包裝類別物件可用 getClass().getSimpleName() 取得型態名稱
        System.out.println("levelObj = " + levelObj + "，型態：" + levelObj.getClass().getSimpleName());
        System.out.println("isOnlineObj = " + isOnlineObj + "，型態：" + isOnlineObj.getClass().getSimpleName());
    }
}
