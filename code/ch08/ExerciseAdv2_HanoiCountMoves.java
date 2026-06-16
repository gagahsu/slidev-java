// Ch08 自學練習：計算河內塔移動次數
// 用遞迴方式推導搬移 n 個盤子總共需要的移動次數，不直接套用 2^n - 1 公式，
// 而是從遞迴結構自然推導出 T(n) = 2 * T(n-1) + 1，展開後恰好等於 2^n - 1

public class ExerciseAdv2_HanoiCountMoves {
    static int countMoves(int n) {
        if (n == 1) return 1;            // Base Case：只剩 1 個盤子，只需移動 1 次
        return countMoves(n - 1)         // 先搬 n-1 個到中轉柱
             + 1                          // 移動第 n 個盤子
             + countMoves(n - 1);         // 再搬 n-1 個到目標柱
    }

    public static void main(String[] args) {
        int n = 4;
        System.out.println(countMoves(n));            // 15
        System.out.println((int) Math.pow(2, n) - 1); // 15，驗證與公式一致
    }
}
