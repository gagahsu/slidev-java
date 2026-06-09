import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * 練習二：時間格式轉換
 * 任務 A：格式化輸出現在時間（含星期幾，以中文顯示）
 * 任務 B：解析字串 "2024/05/13 10:30" 並取出年份、月份、星期幾
 */
public class DateTimeFormatterDemo {
    public static void main(String[] args) {
        // 任務 A：格式化輸出
        // 使用 Locale.TRADITIONAL_CHINESE 讓 E 模式輸出中文星期
        DateTimeFormatter fmtA = DateTimeFormatter.ofPattern(
            "yyyy 年 MM 月 dd 日 HH:mm:ss（E）",
            Locale.TRADITIONAL_CHINESE
        );
        System.out.println("任務 A - 格式化輸出：");
        System.out.println(LocalDateTime.now().format(fmtA));
        // e.g. 2024 年 05 月 13 日 10:30:00（週一）

        // 任務 B：解析字串
        System.out.println("\n任務 B - 解析字串：");
        DateTimeFormatter fmtB = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime ldt = LocalDateTime.parse("2024/05/13 10:30", fmtB);
        System.out.println("年份：" + ldt.getYear());        // 2024
        System.out.println("月份：" + ldt.getMonth());       // MAY
        System.out.println("星期幾：" + ldt.getDayOfWeek()); // MONDAY
    }
}
