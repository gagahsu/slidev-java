// Ch06 練習：星號矩形
// 練習巢狀 for 迴圈：基本版印滿矩形，進階版只印邊框

public class Exercise_StarRectangle {
    public static void main(String[] args) {
        int rows = 5, cols = 8;

        // 基本版：填滿矩形
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                System.out.print("*");
            }
            System.out.println();
        }

        System.out.println();

        // 進階版：只印邊框
        // 證照常考：第一列、最後一列、第一欄、最後一欄都屬於邊框
        for (int i = 1; i <= rows; i++) {
            for (int j = 1; j <= cols; j++) {
                if (i == 1 || i == rows || j == 1 || j == cols) {
                    System.out.print("*");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
