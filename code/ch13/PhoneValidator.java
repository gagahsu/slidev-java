import java.util.Scanner;

/**
 * 練習 1：電話號碼格式驗證
 * 支援三種格式：
 *   1. 行動電話：0XXX-XXX-XXX
 *   2. 市內電話（含括號）：(02)12345678
 *   3. 市內電話（含連字號）：02-12345678
 */
public class PhoneValidator {

    // 三種格式用 | 組合
    private static final String REGEX =
            "0\\d{3}-\\d{3}-\\d{3}"           // 行動電話
          + "|\\(0\\d{1,3}\\)\\d{7,8}"         // 市內（括號）
          + "|0\\d{1,3}-\\d{7,8}";             // 市內（連字號）

    public static void main(String[] args) {
        // 範例測試
        String[] samples = {
            "0912-345-678",   // ✅ 行動電話
            "(02)12345678",   // ✅ 市內（括號）
            "02-12345678",    // ✅ 市內（連字號）
            "(049)1234567",   // ✅ 市內（括號，3碼區碼）
            "12345678",       // ❌ 缺少 0 開頭
            "0912345678",     // ❌ 格式不符
        };

        System.out.println("=== 預設測試 ===");
        for (String phone : samples) {
            System.out.printf("%-20s → %s%n", phone, validate(phone) ? "✅ 合法" : "❌ 不合法");
        }

        // 互動輸入
        System.out.println("\n=== 請輸入電話號碼（輸入 q 結束）===");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            if ("q".equalsIgnoreCase(input)) break;
            System.out.println(input + " → " + (validate(input) ? "✅ 合法" : "❌ 不合法"));
        }
        scanner.close();
    }

    static boolean validate(String phone) {
        return phone.matches(REGEX);
    }
}
