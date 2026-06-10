// Ch20 練習2-2：try-with-resources
// 改寫練習2-1，使用 try-with-resources 語法管理 Scanner 資源
// Scanner 會在 try 區塊結束後自動關閉，無需手動呼叫 close()

import java.util.InputMismatchException;
import java.util.Scanner;

public class Exercise2_2_TryWithResources {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("請輸入第一個整數：");
            int num1 = scanner.nextInt();
            System.out.print("請輸入第二個整數（除數）：");
            int num2 = scanner.nextInt();
            int result = num1 / num2;
            System.out.println("結果：" + result);
        } catch (ArithmeticException e) {
            System.out.println("錯誤：除數不能為 0 — " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("錯誤：輸入必須是整數 — " + e);
        }
        // Scanner 已由 try-with-resources 自動關閉，不需要 finally
    }
}
