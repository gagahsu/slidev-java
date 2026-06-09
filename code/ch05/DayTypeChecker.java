import java.util.Scanner;

/**
 * 練習二：星期幾判斷器
 * 使用 Switch Expression（Java 14+）判斷工作日或假日
 * 輸入 1–7（1=星期一，7=星期日），輸出「工作日」、「假日」或「無效輸入」
 */
public class DayTypeChecker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("請輸入星期幾（1–7）：");
        int day = scanner.nextInt();

        // Switch Expression（Java 14+）：多值 case，無需 break
        String type = switch (day) {
            case 1, 2, 3, 4, 5 -> "工作日";
            case 6, 7           -> "假日";
            default             -> "無效輸入";
        };

        System.out.println(type);
        scanner.close();
    }
}
