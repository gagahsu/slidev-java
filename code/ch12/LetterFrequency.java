import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 練習三：字母頻率統計
 * 1. 統計 "AABCBDCDACBDA" 中 A、B、C、D 各出現幾次
 * 2. 挑戰：對任意字串統計所有字元出現次數
 */
public class LetterFrequency {
    public static void main(String[] args) {
        String str = "AABCBDCDACBDA";

        // 第一部分：固定統計 A B C D
        System.out.println("字串：" + str);
        System.out.println("--- 固定統計 A B C D ---");
        char[] targets = {'A', 'B', 'C', 'D'};
        for (char c : targets) {
            int count = 0;
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == c) count++;
            }
            System.out.println(c + " 出現 " + count + " 次");
        }

        // 第二部分：對任意字串統計所有字元
        System.out.println("\n--- 通用統計（任意字串）---");
        Map<Character, Integer> freq = new TreeMap<>(); // TreeMap 讓輸出按字母排序
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        for (Map.Entry<Character, Integer> entry : freq.entrySet()) {
            System.out.println(entry.getKey() + " 出現 " + entry.getValue() + " 次");
        }
    }
}
