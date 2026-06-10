// Ch20 練習2-1：多 catch 實作
// 讀取使用者輸入的 2 個整數，計算除法結果
// 正確捕捉 ArithmeticException（除數為0）和 InputMismatchException（輸入非數字）

import java.util.InputMismatchException;
import java.util.Scanner;

public class Exercise2_1_MultiCatch {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
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
        } finally {
            scanner.close();
        }
    }
}
