import java.util.Scanner;

// Ch04 練習：比較與邏輯運算子
// 依年齡與是否持有學生證，判斷購票類型（優惠票／全票／兒童票）

public class Exercise3_TicketPricing {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("請輸入年齡：");
        int age = scanner.nextInt();

        System.out.print("是否持有學生證（1=有, 0=無）：");
        boolean hasStudentCard = scanner.nextInt() == 1;

        // 判斷順序很重要：先檢查條件最寬鬆的「優惠票」（用 ||），
        // 再檢查「全票」（用 && 串接範圍與排除學生證），其餘才是兒童票
        if (age >= 65 || hasStudentCard) {
            System.out.println("優惠票");
        } else if (age >= 6 && age <= 64 && !hasStudentCard) {
            System.out.println("全票");
        } else {
            System.out.println("兒童票");
        }

        scanner.close();
    }
}
