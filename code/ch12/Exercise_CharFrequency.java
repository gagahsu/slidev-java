// Ch12 練習：字母頻率統計
// 統計字串 "AABCBDCDACBDA" 中每個字元出現的次數
// 挑戰：改用 HashMap 讓程式可處理任意字串

import java.util.HashMap;
import java.util.Map;

public class Exercise_CharFrequency {
    public static void main(String[] args) {
        String text = "AABCBDCDACBDA";

        // 方法一：針對已知字元 A B C D 各用一個變數計數
        System.out.println("=== 方法一：固定四個計數器 ===");
        int countA = 0, countB = 0, countC = 0, countD = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if      (c == 'A') countA++;
            else if (c == 'B') countB++;
            else if (c == 'C') countC++;
            else if (c == 'D') countD++;
        }
        System.out.println("A=" + countA + ", B=" + countB + ", C=" + countC + ", D=" + countD);

        // 方法二（挑戰）：用 HashMap 統計任意字串的字元頻率
        // getOrDefault(key, 0) 取得目前計數，不存在時預設為 0，再 +1 後存回去
        System.out.println("\n=== 方法二：HashMap（可處理任意字串）===");
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : text.toCharArray()) {
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        }
        System.out.println(freq); // {A=4, B=3, C=3, D=3}（順序可能不同）
    }
}
