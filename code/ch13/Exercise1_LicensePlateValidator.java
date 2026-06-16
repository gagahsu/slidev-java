// Ch13 練習：車牌格式驗證
// 驗證輸入字串是否符合「小型車：3 個大寫字母 + - + 4 個數字」
// 或「機車：3 個數字 + - + 3 個大寫字母」格式

public class Exercise1_LicensePlateValidator {

    // [A-Z]{3}-[0-9]{4} 對應小型車格式；[0-9]{3}-[A-Z]{3} 對應機車格式
    // 兩者用 | 組合，符合任一種就算合法
    private static final String REGEX = "[A-Z]{3}-[0-9]{4}|[0-9]{3}-[A-Z]{3}";

    public static void main(String[] args) {
        String[] samples = {
            "ABC-1234",   // ✅ 小型車格式
            "123-ABC",    // ✅ 機車格式
            "abc-1234",   // ❌ 小寫字母（[A-Z] 只接受大寫，這是常見的陷阱）
            "AB-1234",    // ❌ 字母數量不足
            "123-4567",   // ❌ 不符合任一格式
        };

        for (String plate : samples) {
            System.out.printf("%-10s → %s%n", plate, plate.matches(REGEX) ? "✅ 合法" : "❌ 不合法");
        }
    }
}
