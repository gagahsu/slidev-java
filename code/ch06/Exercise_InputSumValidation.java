// Ch06 練習：輸入驗證與累加
// 練習 while(true) + hasNextInt() 輸入驗證，搭配 continue 與 break

import java.util.Scanner;

public class Exercise_InputSumValidation {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int sum = 0;
        while (true) {
            if (!sc.hasNextInt()) {
                System.out.println("格式錯誤，請重新輸入");
                sc.next();
                continue; // 業界慣例：丟棄無效輸入後，回到迴圈開頭重新檢查
            }
            int num = sc.nextInt();
            if (num == 0) {
                break;
            }
            sum += num;
        }
        System.out.println("總和：" + sum);
    }
}
