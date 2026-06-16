// Ch06 練習：簡易選單系統
// 練習 do-while 迴圈：選單至少要先顯示一次，才能讓使用者選擇

import java.util.Scanner;

public class Exercise4_BankMenu {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        do {
            System.out.println("1. 查詢餘額");
            System.out.println("2. 存款");
            System.out.println("3. 離開");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> System.out.println("目前餘額：1000 元");
                case 2 -> System.out.println("存款成功");
                case 3 -> System.out.println("再見");
                default -> System.out.println("無效選項");
            }
        } while (choice != 3); // 業界慣例：結尾分號別忘了，這是 do-while 容易漏掉的小細節
    }
}
