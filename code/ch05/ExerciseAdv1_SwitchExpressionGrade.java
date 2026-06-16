import java.util.Scanner;

// Ch05 進階練習：成績等第（Switch Expression 版）
// 用 Switch Expression（Java 14+）依十位數判斷等第，滿分另加註文字

public class ExerciseAdv1_SwitchExpressionGrade {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("請輸入分數（0-100）：");
        int score = scanner.nextInt();

        // 把分數「降維」成十位數，90~99 與 100 都會落在 10 或 9 這兩個 case
        int tens = score / 10;

        String grade = switch (tens) {
            case 10, 9 -> {
                // block 形式的每一條路徑都必須要有 yield
                if (score == 100) {
                    yield "A（滿分！）";
                }
                yield "A";
            }
            case 8 -> "B";
            case 7 -> "C";
            case 6 -> "D";
            default -> "F";
        };

        System.out.println("等第：" + grade);
        scanner.close();
    }
}
