// Ch11 進階練習：台北與紐約的會議時間
// 以 withZoneSameInstant() 將台北時間的會議時刻換算成紐約當地時間

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ExerciseAdv1_TaipeiNewYorkMeeting {
    public static void main(String[] args) {
        // 時區 ID 採用「大陸/城市」格式，避免使用 CST、GMT+8 等容易混淆的縮寫
        ZoneId taipei = ZoneId.of("Asia/Taipei");
        ZoneId newYork = ZoneId.of("America/New_York");

        LocalDateTime ldt = LocalDateTime.of(2024, 5, 13, 21, 0);
        ZonedDateTime taipeiTime = ZonedDateTime.of(ldt, taipei);

        // withZoneSameInstant()：同一個瞬間，換另一個時區的「當地時間」來顯示
        // 注意：與 plusHours() 不同，這個方法不會改變實際發生的時刻
        ZonedDateTime newYorkTime = taipeiTime.withZoneSameInstant(newYork);

        System.out.println("台北時間：" + taipeiTime);
        System.out.println("紐約時間：" + newYorkTime);
    }
}
