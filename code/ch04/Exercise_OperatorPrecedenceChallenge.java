// Ch04 練習二（綜合）：優先順序大挑戰
// 手動推算混合位元運算子、邏輯運算子與比較運算子的執行順序，再用程式驗證

public class Exercise_OperatorPrecedenceChallenge {
    public static void main(String[] args) {
        int x = 6, y = 3;

        // 1. 算術／位移（第 3-5 層）先算：x % y, x << 1
        //    x % y = 0, x << 1 = 12

        // 2. 比較運算子（第 6-7 層）：x > y, (x % y) == 0, (x << 1) > 10
        //    x > y = true, 0 == 0 = true, 12 > 10 = true

        // 3. 位元 AND（第 8 層，比 || 優先）：true & true = true
        // 4. 邏輯 OR（第 12 層）：true || true = true

        // 證照常考：&（第 8 層）優先順序比 ||（第 12 層）高，
        // 所以會先算 (x > y & x % y == 0)，再跟 (x << 1 > 10) 做 ||
        boolean check = x > y & x % y == 0 || x << 1 > 10;
        System.out.println(check); // true

        // 業界建議：實際開發時遇到混合運算子，務必加括號明確標示意圖
        boolean checkWithParens = (x > y) & ((x % y) == 0) || ((x << 1) > 10);
        System.out.println(checkWithParens); // true，與上式結果相同
    }
}
