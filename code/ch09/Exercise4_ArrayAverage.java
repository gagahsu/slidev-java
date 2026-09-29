// Ch09 練習4：陣列平均分數（挑戰）
// 練習陣列當參數傳入方法，搭配迴圈計算平均值

public class Exercise4_ArrayAverage {

    public static double calculateAverage(int[] scores) {
        int sum = 0;
        for (int i = 0; i < scores.length; i++) {
            sum += scores[i];
        }
        return (double) sum / scores.length;
    }

    public static void main(String[] args) {
        int[] scores = {85, 90, 78, 92};
        System.out.println(calculateAverage(scores)); // 86.25
    }
}
