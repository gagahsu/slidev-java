// Ch09 練習1：溫度轉換器（基礎）
// 練習單一參數 + 回傳值，回傳型別為 double

public class Exercise1_TemperatureConverter {

    public static double celsiusToFahrenheit(double celsius) {
        return celsius * 9.0 / 5.0 + 32;
    }

    public static void main(String[] args) {
        double f = celsiusToFahrenheit(25.0);
        System.out.println(f); // 77.0
    }
}
