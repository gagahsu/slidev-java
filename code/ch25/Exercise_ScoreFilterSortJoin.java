// Ch25 練習二：成績處理綜合練習
// Stream：filter -> sorted(reverseOrder) -> map(String::valueOf) -> Collectors.joining -> reduce

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Exercise_ScoreFilterSortJoin {

    public static void main(String[] args) {
        List<Integer> scores = List.of(45, 78, 90, 62, 55, 85, 91, 73);

        // 1. 篩選出及格（>= 60）的成績，依分數由高到低排序
        List<Integer> passed = scores.stream()
            .filter(s -> s >= 60)
            .sorted(Comparator.reverseOrder())
            .toList();

        // 2. 用 map 把 Integer 轉成 String，再用 Collectors.joining 串接
        String joined = passed.stream()
            .map(String::valueOf)
            .collect(Collectors.joining("、"));
        System.out.println(joined); // 91、90、85、78、73、62

        // 3. 用 reduce 計算所有及格成績的總和
        int total = passed.stream().reduce(0, Integer::sum);
        System.out.println("及格總分：" + total); // 479
    }
}
