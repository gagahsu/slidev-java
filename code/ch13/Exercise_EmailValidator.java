// Ch13 練習：Email 格式驗證
// 驗證 Email 是否符合以下結構：
// 帳號（[\w.+-]+）@ 網域（[\w-]+）頂級網域（(\.[\w-]{2,})+，可重複多層）
// 合法範例：user@example.com、hello+tag@mail.co.uk、a.b-c@x-y.org

import java.util.Scanner;

public class Exercise_EmailValidator {

    // 帳號：[\w.+-]+      \w = [A-Za-z0-9_]，加上 . + -
    // @：直接寫 @
    // 網域：[\w-]+
    // 頂級網域：(\.[\w-]{2,})+  用 + 允許重複（.com.tw 這種多層網域）
    private static final String REGEX = "[\\w.+-]+@[\\w-]+(\\.([\\w-]{2,}))+";

    public static void main(String[] args) {
        String[] samples = {
            "user@example.com",      // ✅
            "hello+tag@mail.co.uk",  // ✅ 多層頂級網域
            "a.b-c@x-y.org",        // ✅ 帳號與網域含特殊符號
            "bad.address",           // ❌ 缺少 @
            "user@.com",             // ❌ 網域開頭不合法
            "@example.com",          // ❌ 帳號為空
            "user@example",          // ❌ 頂級網域不足 2 個字元
        };

        System.out.println("Email 格式驗證：");
        for (String email : samples) {
            System.out.printf("%-30s → %s%n", email,
                    email.matches(REGEX) ? "✅ 合法" : "❌ 不合法");
        }

        // 互動輸入版本
        System.out.println("\n請輸入 Email（輸入 exit 離開）：");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            if (input.equals("exit")) break;
            System.out.println(input.matches(REGEX) ? "✅ 合法" : "❌ 不合法");
        }
        scanner.close();
    }
}
