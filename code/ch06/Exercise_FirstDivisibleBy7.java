// Ch06 練習：找出第一個符合條件的數字
// 練習無限迴圈搭配 continue（跳過奇數）與 break（找到答案即結束）

public class Exercise_FirstDivisibleBy7 {
    public static void main(String[] args) {
        for (int i = 1;; i++) {
            if (i % 2 != 0) {
                continue; // 跳過奇數
            }
            if (i % 7 == 0) {
                System.out.println("找到了：" + i);
                break; // 找到答案，結束迴圈
            }
            System.out.println("跳過：" + i);
        }
    }
}
