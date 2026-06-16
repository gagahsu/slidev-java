// Ch06 練習：倒數計時器
// 練習 while 迴圈的遞減寫法：條件與更新方向必須一致

public class Exercise3_CountdownTimer {
    public static void main(String[] args) {
        int count = 5;
        while (count >= 1) {
            System.out.println("倒數：" + count);
            count--;
        }
        System.out.println("時間到！");
    }
}
