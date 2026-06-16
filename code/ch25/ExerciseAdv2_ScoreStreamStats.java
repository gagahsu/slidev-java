// Ch25 進階練習二：成績串流統計
// Stream：filter + count、max、mapToInt().average() 等 Primitive Stream 操作

import java.util.Comparator;
import java.util.List;

public class ExerciseAdv2_ScoreStreamStats {

    public static void main(String[] args) {
        List<Integer> s = List.of(45, 78, 90, 62, 55, 85, 91, 73);

        // 1. 計算及格（>= 60）的人數：filter + count
        long cnt = s.stream()
            .filter(x -> x >= 60)
            .count();

        // 2. 找出所有成績中的最高分：max 回傳 Optional，用 .get() 取出
        int max = s.stream()
            .max(Comparator.naturalOrder())
            .get();

        // 3. 計算及格學生的平均分：先 filter 排除不及格，再 mapToInt 轉成 IntStream，
        //    呼叫 .average() 回傳 OptionalDouble，用 .getAsDouble() 取出
        double avg = s.stream()
            .filter(x -> x >= 60)
            .mapToInt(Integer::intValue)
            .average()
            .getAsDouble();

        System.out.printf("及格：%d 人，最高：%d，平均：%.1f%n", cnt, max, avg);
        // 預期：及格：6 人，最高：91，平均：79.8
    }
}
