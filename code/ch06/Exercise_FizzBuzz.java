// Ch06 練習一：FizzBuzz
// 輸出 1 到 50，能被 3 整除輸出 Fizz，能被 5 整除輸出 Buzz，
// 能被 15 整除輸出 FizzBuzz，其餘輸出數字本身

public class Exercise_FizzBuzz {
    public static void main(String[] args) {
        for (int i = 1; i <= 50; i++) {
            // 證照常考：最嚴格條件（i % 15 == 0）必須放在最前面，
            // 否則會先被 i % 3 或 i % 5 的條件攔截，FizzBuzz 永遠不會印出
            if (i % 15 == 0)     System.out.print("FizzBuzz ");
            else if (i % 3 == 0) System.out.print("Fizz ");
            else if (i % 5 == 0) System.out.print("Buzz ");
            else                 System.out.print(i + " ");
        }
        System.out.println(); // 換行
    }
}
