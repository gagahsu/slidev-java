// Ch18 練習三：進位制轉換器
// 讀取十進位整數，印出二進位、八進位和十六進位表示

import java.util.Scanner;

public class Exercise3_BaseConverter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("請輸入一個十進位整數：");
        int n = Integer.parseInt(sc.nextLine());
        sc.close();

        System.out.println("二進位：" + Integer.toBinaryString(n));
        System.out.println("八進位：" + Integer.toOctalString(n));
        System.out.println("十六進位：" + Integer.toHexString(n));
    }
}
