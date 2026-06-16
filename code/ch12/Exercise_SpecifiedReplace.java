// Ch12 練習：指定取代
// 將長字串中「最後一個」禰豆子取代為「竹筒」，其餘不動
// 關鍵：用 lastIndexOf 找到最後一個位置，再用 substring 切開後重新拼接

public class Exercise_SpecifiedReplace {
    public static void main(String[] args) {
        String text = "鬼滅之刃是炭治郎與禰豆子的故事，我不喜歡禰豆子的那田蜘蛛山，雖然禰豆子在炭治郎眼中是清新脫俗";
        String target = "禰豆子";
        String replacement = "竹筒";

        // lastIndexOf：從字串末尾往前找，取得最後一個目標的起始位置
        int lastIndex = text.lastIndexOf(target);

        if (lastIndex != -1) {
            // 切成三段再拼接：前半段 + 替換詞 + 後半段
            String result = text.substring(0, lastIndex)
                    + replacement
                    + text.substring(lastIndex + target.length());
            System.out.println("原始：" + text);
            System.out.println("取代：" + result);
        } else {
            System.out.println("找不到目標字串");
        }
    }
}
