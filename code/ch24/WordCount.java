// 練習 4-2：單字計數器
// Map.getOrDefault() 統計次數，HashMap vs TreeMap 順序比較

import java.util.*;

public class WordCount {

    public static void main(String[] args) {
        String[] words = {"apple", "banana", "apple", "orange", "banana", "apple"};

        // HashMap：不保證順序
        Map<String, Integer> hashCount = new HashMap<>();
        for (String w : words) {
            hashCount.put(w, hashCount.getOrDefault(w, 0) + 1);
        }
        System.out.println("HashMap：" + hashCount);

        // TreeMap：依 key 字母順序
        Map<String, Integer> count = new TreeMap<>();
        for (String w : words) {
            count.put(w, count.getOrDefault(w, 0) + 1);
        }
        System.out.println("TreeMap：" + count);

        String maxWord = null;
        int maxCount = 0;
        for (var entry : count.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                maxWord = entry.getKey();
            }
        }
        System.out.println("出現最多次：" + maxWord + "（" + maxCount + " 次）");
    }
}
