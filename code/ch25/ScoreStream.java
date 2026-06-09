// 練習二：成績串流統計
// Stream：count、max、mapToInt().average()

import java.util.*;
import java.util.stream.*;

public class ScoreStream {

    public static void main(String[] args) {
        List<Integer> scores = List.of(45, 78, 90, 62, 55, 85, 91, 73);

        // 1. 計算及格（>= 60）的人數
        long passCount = scores.stream()
            .filter(x -> x >= 60)
            .count();

        // 2. 找出最高分（Comparator.naturalOrder() 等同於自然排序）
        int max = scores.stream()
            .max(Comparator.naturalOrder())
            .get();

        // 3. 計算及格學生的平均分（先 filter，再 mapToInt 轉 IntStream，最後 average）
        double avg = scores.stream()
            .filter(x -> x >= 60)
            .mapToInt(Integer::intValue)
            .average()
            .getAsDouble();

        System.out.printf("及格：%d 人，最高：%d，平均：%.1f%n", passCount, max, avg);
    }
}
