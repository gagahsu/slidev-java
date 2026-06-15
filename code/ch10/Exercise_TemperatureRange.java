// Ch10 練習：溫度範圍限制與溫差計算
// 練習 Math.max()/Math.min() 做數值限制（Clamping），以及 Math.abs() 計算絕對值溫差

public class Exercise_TemperatureRange {
    public static void main(String[] args) {
        int currentRoomTemp = 25;

        // 情況一：temp = 35（超出上限），限制在 16~30 之間
        int temp1 = 35;
        int clamped1 = Math.min(Math.max(temp1, 16), 30);
        System.out.println(clamped1); // 30

        // 情況二：temp = 10（超出下限）
        int temp2 = 10;
        int clamped2 = Math.min(Math.max(temp2, 16), 30);
        System.out.println(clamped2); // 16

        // 溫差（一律取絕對值，不分正負）
        int diff = Math.abs(clamped1 - currentRoomTemp);
        System.out.println("溫差：" + diff); // 溫差：5
    }
}
