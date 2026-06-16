// Ch13 練習一：偵測重複單字與密碼格式
// 1. 使用反向引用判斷文字中是否含有連續重複的單字
// 2. 使用環視斷言驗證密碼是否同時符合長度、數字、大寫字母三個條件

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExerciseAdv2_DuplicateWordAndPasswordValidator {

    public static void main(String[] args) {
        detectDuplicateWord();
        validatePassword();
    }

    // 1. 反向引用 \1：(\w+) 抓到一個單字，\1 要求後面緊接著相同的單字
    // \b 確保是完整單字邊界，避免誤判 "there there" 之類的情況
    static void detectDuplicateWord() {
        String[] texts = {
            "the the cat sat",   // 含有連續重複單字 "the the"
            "the cat sat there", // 沒有連續重複單字
        };

        Pattern pattern = Pattern.compile("\\b(\\w+) \\1\\b");
        for (String text : texts) {
            Matcher m = pattern.matcher(text);
            System.out.println("\"" + text + "\" → 含重複單字？ " + m.find());
        }
    }

    // 2. 環視斷言：多個 (?=...) 可一個接一個疊加，每個各自從同一起點檢查一次
    //    (?=.*\d)      至少包含 1 個數字
    //    (?=.*[A-Z])   至少包含 1 個大寫字母
    //    .{8,}         長度至少 8 個字元
    static void validatePassword() {
        String passRegex = "^(?=.*\\d)(?=.*[A-Z]).{8,}$";

        String[] passwords = {
            "Pass1234", // ✅ 長度 8、有數字、有大寫
            "pass1234", // ❌ 沒有大寫
            "Password", // ❌ 沒有數字
            "Pa1",      // ❌ 長度不足 8
        };

        for (String pwd : passwords) {
            System.out.printf("%-10s → %s%n", pwd, pwd.matches(passRegex) ? "✅ 合法" : "❌ 不合法");
        }
    }
}
