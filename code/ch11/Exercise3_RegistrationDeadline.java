// Ch11 練習：活動報名截止時間
// 將分開儲存的日期與時間合併成 LocalDateTime，再拆解回 LocalDate 與 LocalTime

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Exercise3_RegistrationDeadline {
    public static void main(String[] args) {
        LocalDate date = LocalDate.of(2024, 8, 10);
        LocalTime time = LocalTime.of(23, 59);

        // 合併：LocalDate + LocalTime -> LocalDateTime
        LocalDateTime deadline = LocalDateTime.of(date, time);
        System.out.println(deadline); // 2024-08-10T23:59

        // 拆解：LocalDateTime -> LocalDate / LocalTime
        LocalDate d = deadline.toLocalDate();
        LocalTime t = deadline.toLocalTime();
        System.out.println(d); // 2024-08-10
        System.out.println(t); // 23:59
    }
}
