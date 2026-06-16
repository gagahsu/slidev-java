import java.util.Scanner;

// Ch04 練習一：BMI 計算機
// 輸入身高（cm）與體重（kg），計算 BMI 並判斷體重範圍

public class Exercise_BmiCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("請輸入身高（cm）：");
        double height = scanner.nextDouble();

        System.out.print("請輸入體重（kg）：");
        double weight = scanner.nextDouble();

        // 證照常考：身高必須除以 100.0，若除以 100（整數）會因整數除法嚴重失真
        double heightM = height / 100.0;
        double bmi = weight / (heightM * heightM);

        System.out.printf("BMI = %.2f%n", bmi);

        // 加入 if-else 判斷體重範圍
        if (bmi < 18.5) {
            System.out.println("體重過輕");
        } else if (bmi < 25.0) {
            System.out.println("正常");
        } else {
            System.out.println("過重");
        }

        scanner.close();
    }
}
