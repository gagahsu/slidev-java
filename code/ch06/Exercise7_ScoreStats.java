// Ch06 練習：成績的累加與極值
// 練習在同一個迴圈中同時計算總分、平均、計數、最大值與最小值

public class Exercise7_ScoreStats {
    public static void main(String[] args) {
        int[] scores = {72, 88, 65, 91, 58, 76};
        int sum = 0, count = 0;
        int max = scores[0], min = scores[0];

        for (int score : scores) {
            sum += score;
            if (score >= 80) count++;
            if (score > max) max = score;
            if (score < min) min = score;
        }

        // 證照常考：(double) sum / scores.length 避免整數除法
        double avg = (double) sum / scores.length;
        System.out.printf("總分：%d，平均：%.1f%n", sum, avg);
        System.out.println("高分人數：" + count);
        System.out.println("最高分：" + max + "，最低分：" + min);
    }
}
