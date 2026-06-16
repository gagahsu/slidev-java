// Ch10 練習：猜數字遊戲（產生答案）
// 使用 java.util.Random 的 nextInt(n) 產生 1~50 的答案，並用 nextBoolean() 決定是否為困難模式

import java.util.Random;

public class Exercise4_GuessNumberGame {
    public static void main(String[] args) {
        Random rand = new Random();

        // nextInt(n) 回傳範圍是 [0, n)，要得到 1~50 需要 nextInt(50) + 1
        int answer = rand.nextInt(50) + 1;

        // nextBoolean() 各有 50% 機率回傳 true / false
        boolean hardMode = rand.nextBoolean();

        System.out.println("答案：" + answer);
        System.out.println("困難模式：" + hardMode);
    }
}
