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
        // 方法：用字串轉換處理（數字超過 int 範圍時會出錯，題目 digits 不長時足夠）
        // 1. 拼成字串
        StringBuilder sb = new StringBuilder();
        for (int d : digits) sb.append(d);

        // 2. 轉成 int 運算
        int num = Integer.parseInt(sb.toString()) + 1;

        // 3. 把結果拆回陣列
        String[] parts = String.valueOf(num).split("");
        int[] result = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            result[i] = Integer.parseInt(parts[i]);
        }
        return result;
    }
}
