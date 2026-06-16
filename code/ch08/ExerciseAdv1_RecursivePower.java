// Ch08 自學練習：遞迴計算次方
// 仿照 factorial 的寫法，用遞迴計算 base 的 exp 次方
// Base Case：exp == 0 時回傳 1；Recursive Case：base * power(base, exp - 1)

public class ExerciseAdv1_RecursivePower {
    static int power(int base, int exp) {
        if (exp == 0) return 1;             // Base Case：任何數的 0 次方都是 1
        return base * power(base, exp - 1); // Recursive Case：問題縮小成 exp - 1
    }

    public static void main(String[] args) {
        System.out.println(power(2, 5)); // 32
        // 展開：2 * 2 * 2 * 2 * 2 * power(2,0) = 2^5 * 1 = 32
    }
}
