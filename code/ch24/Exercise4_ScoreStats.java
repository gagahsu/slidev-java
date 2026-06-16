// Ch24 練習 4-1：成績統計系統
// 練習 HashMap 的 put（新增/更新）、values()、entrySet() 遍歷

import java.util.HashMap;
import java.util.Map;

public class Exercise4_ScoreStats {

    public static void main(String[] args) {
        Map<String, Integer> scores = new HashMap<>();
        scores.put("炭治郎", 95);
        scores.put("善逸", 70);
        scores.put("伊之助", 85);
        scores.put("蜜璃", 90);

        // 1. 新增「甘露寺：88」
        scores.put("甘露寺", 88);

        // 2. 將「善逸」的成績更新為 80（再次 put 相同 Key 即覆蓋）
        scores.put("善逸", 80);

        // 3. 計算全班平均（整數除法）
        int total = 0;
        for (int s : scores.values()) total += s;
        System.out.println("平均：" + total / scores.size());

        // 4. 印出所有成績 >= 85 的學生姓名
        // 用 entrySet() 同時取得 Key（名字）與 Value（分數）
        System.out.println("成績 >= 85 的學生：");
        for (var e : scores.entrySet()) {
            if (e.getValue() >= 85) {
                System.out.println("  " + e.getKey());
            }
        }
    }
}
