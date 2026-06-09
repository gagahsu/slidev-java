/**
 * 練習一：出現次數計算
 * 計算「禰豆子」在長字串中出現幾次
 */
public class CountOccurrences {
    public static void main(String[] args) {
        String text = "鬼滅之刃是炭治郎與禰豆子的故事，我不喜歡禰豆子的那田蜘蛛山，雖然禰豆子在炭治郎眼中是清新脫俗";
        String target = "禰豆子";
        int count = 0;
        int index = 0;

        // 使用 indexOf 搭配起始位置，每次找到後跳過已找到的部分
        while ((index = text.indexOf(target, index)) != -1) {
            count++;
            index += target.length(); // 移動起點避免重複計算
        }

        System.out.println("原字串：" + text);
        System.out.println("「" + target + "」出現了 " + count + " 次");
    }
}
