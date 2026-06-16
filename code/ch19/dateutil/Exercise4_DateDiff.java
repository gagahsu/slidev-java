// Ch19 練習 4：計算天數差
// 使用 java.time.LocalDate 與 java.time.temporal.ChronoUnit 計算兩日期相差天數

package dateutil;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Exercise4_DateDiff {
    public static void main(String[] args) {
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.now();

        long days = ChronoUnit.DAYS.between(startDate, endDate);
        System.out.println("相差 " + days + " 天");

        // 業界實務：ChronoUnit.DAYS.between(...) 常用於計算
        // 「會員到期天數」、「訂單已過幾天」等情境
    }
}
