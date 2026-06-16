// Ch11 進階練習：將 LocalDate 轉換為 Date
// 將 LocalDate 透過 atStartOfDay() -> atZone() -> toInstant() 轉換成 java.util.Date

import java.util.Date;
import java.time.LocalDate;
import java.time.ZoneId;

public class ExerciseAdv3_LocalDateToDateConversion {
    public static void main(String[] args) {
        LocalDate localDate = LocalDate.of(2024, 12, 25);

        // LocalDate 沒有時間資訊，須先補上「當天 00:00」變成 LocalDateTime，
        // 再貼上時區標籤變成 ZonedDateTime，最後 toInstant() 抵達 Instant「轉運站」
        Date date = Date.from(
            localDate.atStartOfDay()
                     .atZone(ZoneId.systemDefault())
                     .toInstant()
        );

        System.out.println(date);
    }
}
