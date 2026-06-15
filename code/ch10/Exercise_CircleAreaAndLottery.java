// Ch10 練習：圓形面積與抽號機
// 任務一：用 Math.PI 計算圓面積；任務二：用 Math.random() 產生 1~100 的隨機整數

public class Exercise_CircleAreaAndLottery {
    public static void main(String[] args) {
        // 任務一：半徑 7.0 的圓面積
        double r = 7.0;
        double area = r * r * Math.PI;
        System.out.println(area); // 153.93804002589985

        // 任務二：1~100 的隨機整數
        // 公式：(int)(Math.random() * 範圍大小) + 最小值
        int n = (int) (Math.random() * 100) + 1;
        System.out.println(n); // 1 ~ 100 之間的某個整數
    }
}
