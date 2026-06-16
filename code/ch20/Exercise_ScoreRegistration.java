// Ch20 綜合練習：成績登錄系統
// 整合 try-catch-finally、throw/throws、自訂例外、try-with-resources

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class InvalidScoreException extends Exception {
    private final int score;

    InvalidScoreException(int score) {
        super("分數超出範圍：" + score);
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}

public class Exercise_ScoreRegistration {

    static void addScore(List<Integer> scores, int score) throws InvalidScoreException {
        if (score < 0 || score > 100) {
            throw new InvalidScoreException(score);
        }
        scores.add(score);
        System.out.println("登錄成功：" + score);
    }

    public static void main(String[] args) {
        List<Integer> scores = new ArrayList<>();

        // try-with-resources：Scanner 用完自動關閉，不需手動 close()
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.print("請輸入成績（輸入 -1 結束）：");
                int input = sc.nextInt();

                // 業界常用寫法：結束指令用獨立判斷處理，不混進例外流程
                if (input == -1) {
                    break;
                }

                try {
                    addScore(scores, input);
                } catch (InvalidScoreException e) {
                    System.out.println("成績不合法：" + e.getScore());
                } finally {
                    System.out.println("本次輸入處理完畢");
                }
            }
        }

        // 計算平均分數
        if (scores.isEmpty()) {
            System.out.println("尚無有效成績");
        } else {
            int total = 0;
            for (int s : scores) {
                total += s;
            }
            double average = (double) total / scores.size();
            System.out.println("平均分數：" + average);
        }
    }
}
