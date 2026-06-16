// Ch13 練習：擷取貼文中的標籤
// 使用 Pattern 與 Matcher 找出貼文中所有以 # 開頭的標籤，並印出內容與起始位置

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExerciseAdv3_HashtagExtractor {

    public static void main(String[] args) {
        String post = "今天和 #炭治郎 #禰豆子 一起去 #鬼殺隊 訓練！";

        // #\w+：# 加上一個以上的 \w
        // ⚠️ 易錯點：Java 預設的 \w 只代表 [a-zA-Z_0-9]，不包含中文字！
        // 必須加上 Pattern.UNICODE_CHARACTER_CLASS 旗標，\w 才會涵蓋中文字元，
        // 才能一次抓到「#炭治郎」這種中英混合的標籤
        Matcher m = Pattern.compile("#\\w+", Pattern.UNICODE_CHARACTER_CLASS).matcher(post);

        // while (m.find()) 是處理「文字中可能出現多次符合內容」的標準寫法：
        // 每呼叫一次 find()，Matcher 就往後搜尋下一個符合的片段，直到搜尋不到為止
        while (m.find()) {
            System.out.println(m.group() + "，位置：" + m.start());
        }
        // #炭治郎，位置：4
        // #禰豆子，位置：9
        // #鬼殺隊，位置：18
    }
}
