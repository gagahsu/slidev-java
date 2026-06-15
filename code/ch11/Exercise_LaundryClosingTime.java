// Ch11 練習：洗衣店打烊時間
// 開門時間 10:00，營業 12 小時，計算打烊時間並拆解成「時」與「分」

import java.time.LocalTime;

public class Exercise_LaundryClosingTime {
    public static void main(String[] args) {
        LocalTime open = LocalTime.of(10, 0);

        // plusHours() 不會修改 open，而是回傳一個新的 LocalTime
        LocalTime close = open.plusHours(12);

        System.out.println("開門時間：" + open);
        System.out.println("打烊時間：" + close.getHour() + " 點 " + close.getMinute() + " 分");
        // 打烊時間：22 點 0 分
    }
}
