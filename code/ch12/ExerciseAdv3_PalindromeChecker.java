// Ch12 練習：迴文判斷
// 判斷輸入字串是否為「迴文」（正讀反讀結果相同）
// 關鍵：將字串放入 StringBuilder，呼叫 reverse()，再用 equals() 與原字串比對

public class ExerciseAdv3_PalindromeChecker {
    public static void main(String[] args) {
        String[] tests = {
            "禰豆子豆禰", // 迴文
            "鬼滅之刃",   // 非迴文
            "abcba",     // 迴文
            "hello",     // 非迴文
        };

        for (String input : tests) {
            // 1. 建立 StringBuilder 物件
            // 2. 呼叫 reverse() 取得反轉內容
            // 3. toString() 轉回 String，再用 equals() 比對（不能用 ==）
            String reversed = new StringBuilder(input).reverse().toString();
            boolean isPalindrome = input.equals(reversed);
            System.out.println("\"" + input + "\" → " + (isPalindrome ? "是迴文" : "不是迴文"));
        }
    }
}
