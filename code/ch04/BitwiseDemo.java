import java.util.Scanner;

/**
 * 練習二：進階位元操作
 * 使用位元運算子判斷奇偶、執行快速乘除
 */
public class BitwiseDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("請輸入一個整數：");
        int n = scanner.nextInt();

        // 1. 判斷奇偶：最低位元為 0 代表偶數
        System.out.println((n & 1) == 0 ? n + " 是偶數" : n + " 是奇數");

        // 2. 乘以 4：左移 2 位 = 乘以 2² = 乘以 4
        System.out.println(n + " × 4 = " + (n << 2));

        // 3. 除以 2：右移 1 位 = 除以 2（向下取整）
        System.out.println(n + " ÷ 2 = " + (n >> 1));

        scanner.close();
    }
}
