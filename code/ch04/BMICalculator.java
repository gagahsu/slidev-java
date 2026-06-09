import java.util.Scanner;

/**
 * 練習一：BMI 計算機
 * 讀取使用者輸入的身高（cm）和體重（kg），計算 BMI 並顯示體重狀態
 * BMI 公式：BMI = 體重(kg) / (身高(m))²
 */
public class BMICalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("請輸入身高（cm）：");
        double height = scanner.nextDouble();

        System.out.print("請輸入體重（kg）：");
        double weight = scanner.nextDouble();

        // 身高從公分轉換為公尺
        double heightM = height / 100.0;

        // 計算 BMI
        double bmi = weight / (heightM * heightM);

        System.out.printf("BMI = %.2f%n", bmi);

        // 依 BMI 值判斷體重狀態
        if (bmi < 18.5) {
            System.out.println("體重過輕");
        } else if (bmi < 25.0) {
            System.out.println("體重正常");
        } else {
            System.out.println("體重過重");
        }

        scanner.close();
    }
}
