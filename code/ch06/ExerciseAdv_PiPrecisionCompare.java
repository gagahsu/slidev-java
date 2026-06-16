// Ch06-adv 自學練習二：精度比較專題
// 練習用不同迭代次數估算 π，觀察迭代次數與精確度的關係

public class ExerciseAdv_PiPrecisionCompare {
    public static void main(String[] args) {
        int[] iterations = {1000, 100000, 10000000};
        for (int n : iterations) {
            double pi = 0;
            for (int i = 0; i < n; i++) {
                // 證照常考：2.0 不能省略，否則整段變成 int 除法
                pi += (i % 2 == 0 ? 1 : -1) / (2.0 * i + 1);
            }
            System.out.printf("迭代 %,d 次：π ≈ %.6f%n", n, pi * 4);
        }

        // 延伸思考：若改成 (2 * i + 1)
        // (i % 2 == 0 ? 1 : -1) / (2 * i + 1) 是 int / int
        // 當 i == 0 時結果是 1 / 1 = 1（恰好正確）
        // 但當 i >= 1 時，例如 1 / 3 = 0（整數除法直接變成 0）
        // 所有非零項都被截成 0，最後 pi 永遠是 1，π ≈ 4
    }
}
