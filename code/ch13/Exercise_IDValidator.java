// Ch13 練習：身份證字號驗證
// 台灣身份證格式：1 個英文字母 + 1 個性別碼（1 或 2）+ 8 個數字，共 10 碼
// 進階：排除 6 都首字母（A、B、D、E、F、H，大小寫皆排除）

import java.util.Scanner;

public class Exercise_IDValidator {

    // 基本版：[A-Za-z][12]\d{8}
    private static final String BASIC_REGEX = "[A-Za-z][12]\\d{8}";

    // 進階版：排除 6 都首字母（A B D E F H，大小寫皆排除）
    // [^ABDEFHabdefh] 排除指定字母的大寫與小寫
    private static final String ADVANCED_REGEX = "[^ABDEFHabdefh][12]\\d{8}";

    public static void main(String[] args) {
        String[] samples = {
            "A123456789",  // ✅ 基本格式合法（但進階版排除 A）
            "Z123456789",  // ✅ 基本與進階皆合法
            "A323456789",  // ❌ 第 2 碼是 3，不是 1 或 2
            "AB23456789",  // ❌ 第 2 碼是 B（英文字母），不是 1 或 2
            "a123456789",  // ✅ 基本格式合法（小寫 a，進階版排除）
            "Z12345678",   // ❌ 只有 9 碼，少 1 碼
        };

        System.out.println("=== 基本版驗證 ===");
        for (String id : samples) {
            System.out.printf("%-15s → %s%n", id,
                    id.matches(BASIC_REGEX) ? "✅ 合法" : "❌ 不合法");
        }

        System.out.println("\n=== 進階版（排除 6 都首字母）===");
        for (String id : samples) {
            System.out.printf("%-15s → %s%n", id,
                    id.matches(ADVANCED_REGEX) ? "✅ 合法" : "❌ 不合法");
        }

        // 互動輸入版本
        System.out.println("\n請輸入身份證字號（輸入 exit 離開）：");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            if (input.equals("exit")) break;
            System.out.println("基本：" + (input.matches(BASIC_REGEX) ? "✅ 合法" : "❌ 不合法")
                    + "　進階：" + (input.matches(ADVANCED_REGEX) ? "✅ 合法" : "❌ 不合法"));
        }
        scanner.close();
    }
}
