// Ch12 練習：隊員名單統計與格式化
// 使用 lines() 計算隊員人數，並用 transform() 將整份名單包裝成指定格式

public class ExerciseAdv2_RosterSummary {
    public static void main(String[] args) {
        String roster = "炭治郎\n禰豆子\n善逸\n伊之助";

        // lines()：以換行符切割，回傳 Stream<String>，count() 取得總行數（即隊員人數）
        long count = roster.lines().count();
        System.out.println("隊員人數：" + count); // 4

        // transform()：在呼叫鏈最後插入自訂轉換動作，s 就是 roster 本身的內容
        String result = roster.transform(s -> "隊員名單：" + s);
        System.out.println(result);
    }
}
