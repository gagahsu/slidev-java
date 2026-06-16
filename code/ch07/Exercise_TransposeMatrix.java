// Ch07 綜合練習 2：轉置矩陣
// 練習「i 與 j 互換」的轉置規律：transposed[j][i] = matrix[i][j]

public class Exercise_TransposeMatrix {
    public static void main(String[] args) {
        // 原矩陣 2×3（2 列 3 欄）
        int[][] matrix = {
            {1, 2, 3},
            {4, 5, 6}
        };

        int rows = matrix.length;         // 2
        int cols = matrix[0].length;      // 3

        // 轉置後大小互換：3×2（3 列 2 欄）
        int[][] transposed = new int[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // 核心規律：原本 (i, j) 的元素搬到轉置矩陣的 (j, i)
                transposed[j][i] = matrix[i][j];
            }
        }

        // 用巢狀迴圈印出轉置結果
        for (int i = 0; i < transposed.length; i++) {
            for (int j = 0; j < transposed[i].length; j++) {
                System.out.print(transposed[i][j]);
                if (j < transposed[i].length - 1) System.out.print(" ");
            }
            System.out.println();
        }
        // 預期輸出：
        // 1 4
        // 2 5
        // 3 6
    }
}
