// Ch15 練習：安全處理使用者輸入的暱稱
// 練習使用 Objects.requireNonNullElse() 與 Objects.equals() 安全處理可能為 null 的資料

import java.util.Objects;

public class Exercise1_NicknameUtil {

    // 若 input 為 null，回傳 "訪客"，否則回傳 input
    static String formatNickname(String input) {
        return Objects.requireNonNullElse(input, "訪客");
    }

    // 使用 Objects.equals() 比較，避免 NullPointerException
    // 兩者皆為 null 時視為相同
    static boolean isSameNickname(String a, String b) {
        return Objects.equals(a, b);
    }

    public static void main(String[] args) {
        System.out.println(formatNickname(null));    // 訪客
        System.out.println(formatNickname("古古"));  // 古古

        System.out.println(isSameNickname(null, null));     // true
        System.out.println(isSameNickname(null, "古古"));   // false
        System.out.println(isSameNickname("古古", "古古")); // true
    }
}
