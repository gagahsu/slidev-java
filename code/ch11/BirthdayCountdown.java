import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * 練習一：計算倒數天數
 * 計算今天到下次生日還有幾天
 */
public class BirthdayCountdown {
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();

        // 設定今年生日（以 5 月 20 日為例）
        LocalDate birthday = LocalDate.of(today.getYear(), 5, 20);

        // 若今年生日已過，改算明年生日
        if (birthday.isBefore(today) || birthday.isEqual(today)) {
            birthday = birthday.plusYears(1);
        }

        long days = ChronoUnit.DAYS.between(today, birthday);
        System.out.println("今天：" + today);
        System.out.println("下次生日：" + birthday);
        System.out.println("距離下次生日還有 " + days + " 天");
    }
}
