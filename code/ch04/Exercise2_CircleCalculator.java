import java.util.Scanner;

// Ch04 練習：數學運算與 Math 類別
// 輸入圓的半徑，計算面積、周長，並將面積四捨五入為整數

public class Exercise2_CircleCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("請輸入半徑：");
        double radius = scanner.nextDouble();

        // Math.pow(radius, 2) 與 Math.PI 都是 double，相乘結果也是 double
        double area = Math.pow(radius, 2) * Math.PI;
        double circumference = 2 * radius * Math.PI;

        // 證照常考：Math.round() 回傳型態是 long，不是 int
        long roundedArea = Math.round(area);

        System.out.printf("面積：%.2f%n", area);
        System.out.printf("周長：%.2f%n", circumference);
        System.out.println("面積（四捨五入）：" + roundedArea);

        scanner.close();
    }
}
