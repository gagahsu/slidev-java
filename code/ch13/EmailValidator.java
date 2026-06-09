import java.util.Scanner;

/**
 * 練習 3：Email 格式驗證
 * 帳號部分：英數字、.、+、- 的組合（至少一個字元）
 * @ 符號：必要
 * 網域名稱：英數字與 - 的組合（至少一個字元）
 * 頂級網域：. 加上至少 2 個字元，可出現多次（如 .com.tw）
 */
public class EmailValidator {

    // [\w.+-]+ @ [\w-]+ (\.[\w-]{2,})+
    // 注意：在 Java 字串中 \w 要寫成 \\w
    private static final String EMAIL_REGEX = "[\\w.+-]+@[\\w-]+(\\.([\\w-]{2,}))+";
    // 拆解：
    //   [\\w.+-]+       帳號部分（字母數字底線 . + -，至少 1 個）
    //   @               @ 符號
    //   [\\w-]+         網域名稱（字母數字底線 -，至少 1 個）
    //   (\\.([\\w-]{2,}))+ 頂級網域：點 + 至少 2 個字元，可重複（處理 .com.tw）

    public static void main(String[] args) {
        String[] samples = {
            "user@example.com",       // ✅
            "hello+tag@mail.co.uk",   // ✅
            "a.b-c@x-y.org",          // ✅
            "bad.address",            // ❌ 缺少 @
            "user@.com",              // ❌ 網域開頭不合法
            "@example.com",           // ❌ 帳號部分為空
            "user@domain",            // ❌ 缺少頂級網域
        };

        System.out.printf("%-30s %s%n", "Email", "驗證結果");
        System.out.println("-".repeat(50));
        for (String email : samples) {
            System.out.printf("%-30s %s%n",
                email, email.matches(EMAIL_REGEX) ? "✅ 合法" : "❌ 不合法");
        }

        // 互動輸入
        System.out.println("\n請輸入 Email（輸入 q 結束）：");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            if ("q".equalsIgnoreCase(input)) break;
            System.out.println(input + " → " + (input.matches(EMAIL_REGEX) ? "✅ 合法" : "❌ 不合法"));
        }
        scanner.close();
    }
}
