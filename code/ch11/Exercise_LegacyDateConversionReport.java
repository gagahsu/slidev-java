// Ch11 自學綜合練習：舊系統時間轉換報表
// 將 java.util.Date 轉成 ZonedDateTime（Asia/Tokyo），並計算與今年初的天數差

import java.util.Date;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Exercise_LegacyDateConversionReport {
    public static void main(String[] args) {
        // 模擬舊資料：舊系統資料庫拿到的 Date 型態訂單建立時間
        Date old = new Date();

        ZoneId tokyo = ZoneId.of("Asia/Tokyo");
        // toInstant().atZone(tokyo) 一行完成「轉運站」+「貼時區標籤」
        ZonedDateTime zdt = old.toInstant().atZone(tokyo);

        // 比較基準點：2024-01-01 00:00（Asia/Tokyo），最後一個參數為奈秒
        ZonedDateTime newYear = ZonedDateTime.of(
            2024, 1, 1, 0, 0, 0, 0, tokyo
        );

        // Duration.between() 以 Instant（UTC 瞬間）比較，
        // 只要兩端套用同一時區，換算出的天數差不會因時區不同而改變
        long days = Duration.between(newYear, zdt).toDays();

        System.out.println("訂單時間（東京）：" + zdt);
        System.out.println("距離今年初已過 " + days + " 天");
    }
}
