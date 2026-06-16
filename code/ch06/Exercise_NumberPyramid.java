// Ch06 練習二：數字金字塔
// 用巢狀迴圈印出 n 列的數字金字塔，第 i 列印出 1 到 i

public class Exercise_NumberPyramid {
    public static void main(String[] args) {
        int n = 5;

        // 外層迴圈控制「列數」，內層迴圈控制「每列印幾個數字」
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                System.out.print(j);
                if (j < i) System.out.print(" "); // 數字之間加空格
            }
            System.out.println(); // 每列結束換行
        }
    }
}
