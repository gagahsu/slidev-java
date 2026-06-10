// Ch20 練習1-1：觸發並觀察例外
// 分別觸發 NullPointerException、ArrayIndexOutOfBoundsException、NumberFormatException
// 每次只留一段未註解，執行後觀察錯誤訊息

public class Exercise1_1_TriggerExceptions {
    public static void main(String[] args) {
        // 1. NullPointerException：對 null 的字串呼叫 length()
        String s = null;
        System.out.println(s.length());

        // 2. ArrayIndexOutOfBoundsException：存取陣列不存在的索引
        // int[] arr = new int[3];
        // System.out.println(arr[5]);

        // 3. NumberFormatException：將非數字字串轉成整數
        // int n = Integer.parseInt("Java");
        // System.out.println(n);
    }
}
