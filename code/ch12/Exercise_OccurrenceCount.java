// Ch12 練習：出現次數計算
// 計算「禰豆子」在長字串中出現的次數
// 關鍵：每次找到後，下一次搜尋起點往後移 target.length()，避免重複找到同一個位置

public class Exercise_OccurrenceCount {
    public static void main(String[] args) {
        String text = "鬼滅之刃是炭治郎與禰豆子的故事，我不喜歡禰豆子的那田蜘蛛山，雖然禰豆子在炭治郎眼中是清新脫俗";
        String target = "禰豆子";

        int count = 0;
        int index = text.indexOf(target);

        // 每找到一次就把起點往後移，直到 indexOf 回傳 -1（後面已無符合內容）
        while (index != -1) {
            count++;
            index = text.indexOf(target, index + target.length());
        }

        System.out.println("搜尋字串：" + text);
        System.out.println("「" + target + "」出現次數：" + count); // 3
    }
}
