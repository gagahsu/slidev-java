// Ch11 自學練習一：計算會議倒數時間
// 計算現在到「台北時間明天上午 9 點」的視訊會議還有多久

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Exercise_MeetingCountdown {
    public static void main(String[] args) {
        ZoneId taipei = ZoneId.of("Asia/Taipei");
        ZonedDateTime now = ZonedDateTime.now(taipei);

        // 先取出「明天」這個日期，補上時間 09:00，再貼回時區標籤
        ZonedDateTime meeting = now.toLocalDate()
            .plusDays(1)
            .atTime(9, 0)
            .atZone(taipei);

        Duration d = Duration.between(now, meeting);
        long hours = d.toHours();
        // toMinutes() 回傳「總分鐘數」，要用 % 60 取出扣掉小時後剩下的分鐘數
        long minutes = d.toMinutes() % 60;

        System.out.println("現在時間：" + now);
        System.out.println("會議時間：" + meeting);
        System.out.println("距離會議還有 " + hours + " 小時 " + minutes + " 分鐘");
    }
}
