// Ch06-adv 練習：估算自然對數 e
// 練習用迴圈累加無窮級數，並用「滾動式計算階乘」避免重複運算

public class Exercise_EstimateE {
    public static void main(String[] args) {
        double e = 0;
        double factorial = 1;
        for (int i = 0; i < 20; i++) {
            e += 1 / factorial;
            // 業界慣例：滾動式更新階乘，不必每輪都從頭乘一次
            factorial *= (i + 1);
        }
        System.out.printf("e ≈ %.10f%n", e);
    }
}
