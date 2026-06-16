// Ch11 綜合練習一：計算倒數天數
// 計算今天到下次生日還有幾天，若今年生日已過則改算明年生日
// 業界常用：ChronoUnit.DAYS.between() 計算「總天數」，比 Period 更適合此需求

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Exercise_BirthdayCountdown {
    public static void main(String[] args) {
        LocalDate today = LocalDate.now();

        // 假設生日為每年 5 月 20 日，先建立今年的生日
        LocalDate birthday = LocalDate.of(today.getYear(), 5, 20);

        // 若今年生日已過（isBefore 今天），改算明年生日
        if (birthday.isBefore(today)) {
            birthday = birthday.plusYears(1);
        }

        // ChronoUnit.DAYS.between()：計算兩日期相差的總天數
        // 證照常考：between(from, to) 是從 from 算到 to，順序不能反
        long days = ChronoUnit.DAYS.between(today, birthday);

        System.out.println("距離下次生日還有 " + days + " 天");
    }
}
