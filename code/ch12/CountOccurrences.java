/**
 * 練習一：出現次數計算
 * 計算「禰豆子」在長字串中出現幾次
 */
public class CountOccurrences {
    public static void main(String[] args) {
        String text = "鬼滅之刃是炭治郎與禰豆子的故事，我不喜歡禰豆子的那田蜘蛛山，雖然禰豆子在炭治郎眼中是清新脫俗";
        String target = "禰豆子";
        int count = 0;

        // 先找第一個，找到就計數，再從「目前位置 + 目標長度」繼續找下一個
        int index = text.indexOf(target);
        while (index != -1) {
            count++;
            index = text.indexOf(target, index + target.length());
        }

        System.out.println("原字串：" + text);
        System.out.println("「" + target + "」出現了 " + count + " 次");
    }
}
