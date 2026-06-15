// Ch07 練習：矩陣相減
// 練習矩陣對應位置相減，並用增強型 for 迴圈印出結果

public class Exercise_MatrixSubtraction {
    public static void main(String[] args) {
        int[][] a = {{10, 20}, {30, 40}};
        int[][] b = {{1, 2}, {3, 4}};

        int[][] c = new int[2][2];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length; j++) {
                c[i][j] = a[i][j] - b[i][j];
            }
        }

        // 增強型 for：外層拿一列，內層拿每個值
        for (int[] row : c) {
            for (int val : row) {
                System.out.print(val + "\t");
            }
            System.out.println();
        }
    }
}
