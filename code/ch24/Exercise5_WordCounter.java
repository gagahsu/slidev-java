// Ch24 練習 4-2：單字計數器
// 練習 HashMap + getOrDefault 統計單字出現次數，並找出出現最多次的單字

import java.util.HashMap;
import java.util.Map;

public class Exercise5_WordCounter {

    public static void main(String[] args) {
        String[] words = {"apple", "banana", "apple", "orange", "banana", "apple"};

        // 1. 用 getOrDefault 統計每個單字出現的次數
        // 第一次看到某單字時，Map 裡還沒有它，getOrDefault 回傳 0，加 1 後變成 1
        Map<String, Integer> count = new HashMap<>();
        for (String w : words) {
            count.put(w, count.getOrDefault(w, 0) + 1);
        }

        // 2. 找出出現次數最多的單字
        String maxWord = null;
        int maxCount = 0;
        for (var entry : count.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxWord = entry.getKey();
            }
        }

        // 3. 印出統計結果
        System.out.println("統計結果：" + count);
        System.out.println("出現最多次：" + maxWord + "（" + maxCount + " 次）");
    }
}
