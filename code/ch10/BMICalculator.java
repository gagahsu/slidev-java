import java.util.Scanner;

public class BMICalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("請輸入身高（公分）：");
        double height = sc.nextDouble();

        System.out.print("請輸入體重（公斤）：");
        double weight = sc.nextDouble();

        sc.close();

        double heightM = height / 100.0;                 // 公分轉公尺
        double bmi = weight / Math.pow(heightM, 2);
        double rounded = Math.floor(bmi * 10) / 10.0;   // 取小數點一位

        System.out.printf("BMI：%.1f%n", rounded);

        String status;
        if (bmi < 18.5) {
            status = "體重過輕";
        } else if (bmi < 25.0) {
            status = "正常體重";
        } else if (bmi < 30.0) {
            status = "體重過重";
        } else {
            status = "肥胖";
        }

        System.out.println("體重狀態：" + status);
    }
}
