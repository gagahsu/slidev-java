// Ch06-adv 練習：跳過對角線組合
// 練習迴圈標籤：continue outer 跳過整個外層這一輪，而非只跳過內層這一輪

public class Exercise_SkipDiagonal {
    public static void main(String[] args) {
        outer:
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i == j) {
                    continue outer; // 對角線組合：跳過整個 i，不印出也不繼續這一輪的 j
                }
                System.out.println("(" + i + ", " + j + ")");
            }
        }
    }
}
