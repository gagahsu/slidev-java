import java.util.Scanner;

// Ch04 練習二（綜合）：成績統計小工具
// 輸入三科成績，計算總分、平均，並判斷及格與全優

public class Exercise_GradeStatistics {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("請輸入國文成績：");
        int chinese = scanner.nextInt();

        System.out.print("請輸入數學成績：");
        int math = scanner.nextInt();

        System.out.print("請輸入英文成績：");
        int english = scanner.nextInt();

        int total = chinese + math + english;

        // 業界慣例：除以 3.0（而非 3）才能避免整數除法造成的失真
        double avg = total / 3.0;

        boolean pass = chinese >= 60 && math >= 60 && english >= 60;
        boolean excellent = chinese >= 90 && math >= 90 && english >= 90;

        System.out.println("總分：" + total);
        System.out.printf("平均：%.1f%n", avg);
        System.out.println(pass ? "及格" : "不及格");
        System.out.println(excellent ? "全優" : "未達全優");

        scanner.close();
    }
}
