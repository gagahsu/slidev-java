// Ch07 練習：每列總分
// 練習巢狀 for 走訪二維陣列，計算每一列（每位學生）的小考總分

public class Exercise_RowSums {
    public static void main(String[] args) {
        int[][] scores = {
            {80, 90, 70},
            {60, 75, 85},
            {95, 88, 92}
        };

        for (int i = 0; i < scores.length; i++) {
            int sum = 0; // 每位學生重新從 0 開始累加
            for (int j = 0; j < scores[i].length; j++) {
                sum += scores[i][j];
            }
            System.out.println("學生 " + i + " 總分：" + sum);
        }
    }
}
