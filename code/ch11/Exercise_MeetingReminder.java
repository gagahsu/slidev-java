// Ch11 綜合練習：會議提醒小工具
// 建立會議時間，判斷是否尚未開始，並用 DateTimeFormatter 格式化輸出

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Exercise_MeetingReminder {
    public static void main(String[] args) {
        // 建立一個本月某天下午 2 點的會議時間（範例：2024/05/20 14:00）
        LocalDateTime meeting = LocalDateTime.of(2024, 5, 20, 14, 0);
        LocalDateTime now = LocalDateTime.now();

        // E 模式搭配 Locale.TRADITIONAL_CHINESE 才會輸出中文星期（例如「一」）
        DateTimeFormatter fmt =
            DateTimeFormatter.ofPattern("yyyy/MM/dd（E）HH:mm", Locale.TRADITIONAL_CHINESE);

        System.out.println("會議時間：" + meeting.format(fmt)
            + "，會議尚未開始：" + now.isBefore(meeting));
        // 例如：會議時間：2024/05/20（週一）14:00，會議尚未開始：false
    }
}
