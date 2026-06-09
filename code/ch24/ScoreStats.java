// 練習二：成績統計系統
// HashMap 基本操作：新增、更新、計算平均、篩選高分

import java.util.*;

public class ScoreStats {

    public static void main(String[] args) {
        Map<String, Integer> scores = new HashMap<>();
        scores.put("炭治郎", 95);
        scores.put("善逸",   70);
        scores.put("伊之助", 85);
        scores.put("蜜璃",   90);
        System.out.println("初始成績：" + scores);

        // 1. 新增「甘露寺：88」
        scores.put("甘露寺", 88);
        System.out.println("新增甘露寺後：" + scores);

        // 2. 將「善逸」的成績更新為 80（put 覆蓋舊值）
        scores.put("善逸", 80);
        System.out.println("更新善逸後：" + scores);

        // 3. 計算全班平均分數（整數）
        int total = 0;
        for (int s : scores.values()) total += s;
        System.out.println("平均分數：" + total / scores.size());

        // 4. 印出所有成績 >= 85 的學生姓名
        System.out.print("85 分以上：");
        for (var entry : scores.entrySet()) {
            if (entry.getValue() >= 85) {
                System.out.print(entry.getKey() + " ");
            }
        }
        System.out.println();
    }
}
