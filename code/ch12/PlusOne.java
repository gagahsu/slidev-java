import java.util.Arrays;

/**
 * 練習五：進位運算
 * 給予代表數字的陣列，計算 +1 後的結果陣列
 * 範例 1: [1, 9] → [2, 0]
 * 範例 2: [9, 9, 9] → [1, 0, 0, 0]
 */
public class PlusOne {
    public static void main(String[] args) {
        int[][] testCases = {{1, 9}, {9, 9, 9}, {0}, {1, 2, 3}, {9}};

        for (int[] digits : testCases) {
            int[] result = plusOne(digits);
            System.out.println(Arrays.toString(digits) + " → " + Arrays.toString(result));
        }
    }

    static int[] plusOne(int[] digits) {
        // 方法：用字串轉換處理
        // 1. 拼成字串
        StringBuilder sb = new StringBuilder();
        for (int d : digits) sb.append(d);

        // 2. 轉成 long 運算（防止大數溢位用 BigInteger 更安全，但題目 digits 不長時 long 足夠）
        long num = Long.parseLong(sb.toString()) + 1;

        // 3. 把結果拆回陣列
        String resultStr = String.valueOf(num);
        int[] result = new int[resultStr.length()];
        for (int i = 0; i < resultStr.length(); i++) {
            result[i] = resultStr.charAt(i) - '0'; // char 轉數字
        }
        return result;
    }
}
