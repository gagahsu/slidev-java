// Ch10 綜合練習二：BMI 計算機
// 輸入身高（公分）與體重（公斤），計算 BMI 並判斷體重狀態
// 重點：身高需先換算為公尺（÷100.0）；Math.pow() 計算平方；Math.floor() 取小數一位

public class Exercise_BMICalculator {
    public static void main(String[] args) {
        // 範例資料：身高 170 公分，體重 65 公斤
        double height = 170; // 公分
        double weight = 65.0; // 公斤

        // 證照常考：身高要除以 100.0（不是 100），否則整數除法會截去小數
        double heightM = height / 100.0; // 換算為公尺：1.7

        // BMI = 體重 / 身高²
        double bmi = weight / Math.pow(heightM, 2);

        // 取到小數一位：先乘以 10，取整（floor），再除以 10
        double bmiRounded = Math.floor(bmi * 10) / 10.0;

        System.out.printf("BMI：%.1f%n", bmiRounded);

        // 依 WHO / 台灣標準判斷體重狀態
        if (bmiRounded < 18.5) {
            System.out.println("狀態：體重過輕");
        } else if (bmiRounded < 25.0) {
            System.out.println("狀態：正常體重");
        } else if (bmiRounded < 30.0) {
            System.out.println("狀態：體重過重");
        } else {
            System.out.println("狀態：肥胖");
        }
        // 預期輸出：BMI：22.4 / 狀態：正常體重
    }
}
