import java.util.Scanner;

/**
 * 練習 2：身份證字號驗證
 * 格式：1 個英文字母 + 1 個性別碼（1 或 2）+ 8 個數字，共 10 碼
 *
 * 基本版：第 1 碼大小寫皆可
 * 進階版：排除 6 都首字母（A、B、D、E、F、H，大小寫皆排除）
 *          台灣 6 都：台北(A)、台中(B)、台南(D)、高雄(E)、新北(F)、桃園(H)
 */
public class IdValidator {

    // 基本版：任意英文字母開頭
    private static final String BASIC_REGEX = "[A-Za-z][12]\\d{8}";

    // 進階版：只允許非 6 都首字母 C、G、I~Z（(?i) 讓整個表達式不分大小寫）
    // 注意：不能寫 [^abdefh]，否定集合會讓數字、符號也通過
    private static final String ADVANCED_REGEX = "(?i)[cgi-z][12]\\d{8}";

    public static void main(String[] args) {
        String[] samples = {
            "A123456789",  // ❌ 進階版（A = 台北，6都）、✅ 基本版
            "C123456789",  // ✅ 兩版皆合法
            "Z299999991",  // ✅ 兩版皆合法
            "A300000000",  // ❌ 性別碼不是 1 或 2
            "1123456789",  // ❌ 第一碼不是字母（[^abdefh] 會誤判為合法）
            "c198765432",  // ✅ 兩版（小寫字母轉換）
        };

        System.out.printf("%-15s %-10s %-10s%n", "身份證號", "基本版", "進階版");
        System.out.println("-".repeat(40));
        for (String id : samples) {
            System.out.printf("%-15s %-10s %-10s%n",
                id,
                id.matches(BASIC_REGEX)    ? "✅ 合法" : "❌ 不合法",
                id.matches(ADVANCED_REGEX) ? "✅ 合法" : "❌ 不合法");
        }

        // 互動輸入
        System.out.println("\n請輸入身份證字號（輸入 q 結束）：");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            if ("q".equalsIgnoreCase(input)) break;
            System.out.println("基本版：" + (input.matches(BASIC_REGEX)    ? "✅ 合法" : "❌ 不合法"));
            System.out.println("進階版：" + (input.matches(ADVANCED_REGEX) ? "✅ 合法" : "❌ 不合法"));
        }
        scanner.close();
    }
}
