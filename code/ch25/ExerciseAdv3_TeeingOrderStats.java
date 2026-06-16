// Ch25 進階練習三：teeing 雙重統計
// Collectors.teeing（JDK 12+）：一次 Stream 同時送進兩個收集器，最後合併結果

import java.util.List;
import java.util.stream.Collectors;

public class ExerciseAdv3_TeeingOrderStats {

    public static void main(String[] args) {
        List<Integer> orders = List.of(1200, 350, 4800, 220, 990, 60);

        // 收集器 1：filtering 先篩出 >= 500 的訂單，再用 counting() 數有幾筆
        // 收集器 2：summingInt 直接把所有金額加總（不需篩選）
        // 合併邏輯：兩個收集器的結果分別變成 bigCount 與 total，拼成輸出字串
        // 整段程式碼只走了一次 Stream，效率比寫兩次 stream() 好
        String result = orders.stream()
            .collect(Collectors.teeing(
                Collectors.filtering(a -> a >= 500, Collectors.counting()),
                Collectors.summingInt(a -> a),
                (bigCount, total) -> "大額訂單數：" + bigCount + "，總金額：" + total
            ));

        System.out.println(result); // 大額訂單數：3，總金額：7620
    }
}
