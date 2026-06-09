/**
 * 練習二：指定取代
 * 將字串中「最後一個」禰豆子替換為「竹筒」
 */
public class ReplaceLastOccurrence {
    public static void main(String[] args) {
        String text = "鬼滅之刃是炭治郎與禰豆子的故事，我不喜歡禰豆子的那田蜘蛛山，雖然禰豆子在炭治郎眼中是清新脫俗";
        String target = "禰豆子";
        String replacement = "竹筒";

        // 找到最後一個 target 的位置
        int lastIndex = text.lastIndexOf(target);

        if (lastIndex == -1) {
            System.out.println("找不到目標字串：" + target);
            return;
        }

        // 切開字串後重新拼接
        String before = text.substring(0, lastIndex);
        String after = text.substring(lastIndex + target.length());
        String result = before + replacement + after;

        System.out.println("原字串：" + text);
        System.out.println("替換後：" + result);
    }
}
