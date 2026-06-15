// Ch11 練習：活動是否已經結束
// 判斷活動是否已結束，並計算截止日往後加 30 天的日期與星期幾

import java.time.LocalDate;
import java.time.DayOfWeek;

public class Exercise_EventDeadlineCheck {
    public static void main(String[] args) {
        LocalDate deadline = LocalDate.of(2024, 6, 30);
        LocalDate today = LocalDate.of(2024, 7, 15);

        // isAfter()：今天在截止日之後 -> 活動已結束
        boolean isOver = today.isAfter(deadline);
        System.out.println("活動已結束：" + isOver); // true

        // java.time 物件不可變，plusDays() 會回傳新物件
        LocalDate extended = deadline.plusDays(30);
        System.out.println(extended); // 2024-07-30

        DayOfWeek dow = extended.getDayOfWeek();
        System.out.println(dow); // 對應的星期幾
    }
}
