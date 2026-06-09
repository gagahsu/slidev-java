// Ch18 練習一：讀取使用者輸入並計算
// 使用 Scanner 讀取兩個整數字串，利用 Integer 包裝類別完成轉換與計算

import java.util.Scanner;

public class Exercise1_Scanner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("請輸入第一個整數：");
        String s1 = sc.nextLine();
        System.out.print("請輸入第二個整數：");
        String s2 = sc.nextLine();
        sc.close();

        int n1 = Integer.parseInt(s1);
        int n2 = Integer.parseInt(s2);

        System.out.println("和：" + (n1 + n2));
        System.out.println("差：" + (n1 - n2));
        System.out.println("積：" + (n1 * n2));
        System.out.println("較大值：" + Integer.max(n1, n2));
    }
}
