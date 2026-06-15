// Ch07 練習：成績排行榜
// 練習陣列宣告、Arrays.toString 印出內容、Arrays.sort 原地排序

import java.util.Arrays;

public class Exercise_ScoreLeaderboard {
    public static void main(String[] args) {
        int[] scores = {72, 95, 60, 88, 77};

        System.out.println("原始：" + Arrays.toString(scores));

        // 業界慣例：Arrays.sort 會直接修改原陣列（原地排序）
        Arrays.sort(scores);
        System.out.println("排序後：" + Arrays.toString(scores));

        for (int i = 0; i < scores.length; i++) {
            System.out.println("索引 " + i + "：" + scores[i]);
        }
    }
}
