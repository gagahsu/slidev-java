// Ch13 練習：電話號碼格式驗證
// 驗證輸入是否符合台灣電話格式：
// 1. 行動電話：0XXX-XXX-XXX（0 開頭，4碼-3碼-3碼）
// 2. 市內電話（含括號）：(0X)XXXXXXXX（區碼 2~4 碼，電話 7~8 碼）
// 3. 市內電話（連字號）：0X-XXXXXXXX（區碼 2~4 碼，電話 7~8 碼）

import java.util.Scanner;

public class Exercise_PhoneValidator {

    // 三種格式用 | 組合：
    // 行動電話    0\d{3}-\d{3}-\d{3}
    // 括號市內    \(0\d{1,3}\)\d{7,8}  （括號必須跳脫）
    // 連字號市內  0\d{1,3}-\d{7,8}
    private static final String REGEX =
            "0\\d{3}-\\d{3}-\\d{3}"
            + "|\\(0\\d{1,3}\\)\\d{7,8}"
            + "|0\\d{1,3}-\\d{7,8}";

    public static void main(String[] args) {
        String[] samples = {
            "0912-345-678",   // ✅ 行動電話
            "(02)12345678",   // ✅ 市內（括號）
            "02-12345678",    // ✅ 市內（連字號）
            "12345678",       // ❌ 缺少區碼與格式
            "0912345678",     // ❌ 行動電話缺連字號
        };

        System.out.println("電話號碼格式驗證：");
        for (String phone : samples) {
            System.out.printf("%-20s → %s%n", phone,
                    phone.matches(REGEX) ? "✅ 合法" : "❌ 不合法");
        }

        // 互動輸入版本
        System.out.println("\n請輸入電話號碼（輸入 exit 離開）：");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            if (input.equals("exit")) break;
            System.out.println(input.matches(REGEX) ? "✅ 合法" : "❌ 不合法");
        }
        scanner.close();
    }
}
