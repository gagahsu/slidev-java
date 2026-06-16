// Ch11 綜合練習二：時間格式轉換
// 任務 A：將現在時間以自訂中文格式印出
// 任務 B：將字串 "2024/05/13 10:30" 解析為 LocalDateTime 物件
// 業界常用：DateTimeFormatter.ofPattern() 可同時用於 format()（物件→字串）和 parse()（字串→物件）

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Exercise_DateTimeFormatConversion {
    public static void main(String[] args) {
        // 任務 A：格式化輸出現在時間
        // 注意：pattern 字串中除了格式符號（yyyy、MM 等）以外，
        // 其他文字（中文、括號等）都會原封不動地出現在輸出中
        DateTimeFormatter fmtA = DateTimeFormatter.ofPattern("yyyy 年 MM 月 dd 日 HH:mm:ss（E）");
        String formatted = LocalDateTime.now().format(fmtA);
        System.out.println(formatted);
        // 範例輸出：2024 年 05 月 13 日 10:30:00（星期一）

        // 任務 B：解析字串為 LocalDateTime
        // 證照常考：parse() 時格式必須與字串完全一致，多一個空格都會拋出 DateTimeParseException
        DateTimeFormatter fmtB = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime ldt = LocalDateTime.parse("2024/05/13 10:30", fmtB);

        System.out.println(ldt.getYear());       // 2024
        System.out.println(ldt.getMonth());      // MAY
        System.out.println(ldt.getDayOfWeek()); // MONDAY
    }
}
