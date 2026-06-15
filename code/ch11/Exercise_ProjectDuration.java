// Ch11 進階練習：專案執行時長
// 用 Period.between() 計算啟動日到結案日之間的年、月、日差距

import java.time.LocalDate;
import java.time.Period;

public class Exercise_ProjectDuration {
    public static void main(String[] args) {
        LocalDate start = LocalDate.of(2023, 2, 10);
        LocalDate end = LocalDate.of(2024, 5, 13);

        Period p = Period.between(start, end);

        // 注意：getYears()/getMonths()/getDays() 各自是「餘數」，不是累計加總
        // 若要計算「總共經過幾天」，要改用 ChronoUnit.DAYS.between()
        System.out.println("專案執行了 " + p.getYears() + " 年 "
            + p.getMonths() + " 個月 " + p.getDays() + " 天");
        // 專案執行了 1 年 3 個月 3 天
    }
}
