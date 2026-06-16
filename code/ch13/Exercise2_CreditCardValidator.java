// Ch13 練習：信用卡號格式驗證
// 驗證輸入字串是否符合「4 組數字、每組 4 位、組間用 - 分隔」的信用卡號格式

public class Exercise2_CreditCardValidator {

    // \d{4} 比對第一組 4 位數字
    // (-\d{4}) 把「連字號 + 4 位數字」打包成一個群組，{3} 要求重複 3 次
    // 這種「固定開頭 + 重複群組」的寫法在驗證有規律分段的格式（信用卡號、IP 位址）時很常見
    private static final String REGEX = "\\d{4}(-\\d{4}){3}";

    public static void main(String[] args) {
        String[] samples = {
            "1234-5678-9012-3456", // ✅ 共 4 組，符合
            "1234-5678-9012",      // ❌ 只有 3 組
            "1234-5678-9012-345",  // ❌ 最後一組不足 4 位
            "1234567890123456",    // ❌ 缺少連字號
        };

        for (String card : samples) {
            System.out.printf("%-25s → %s%n", card, card.matches(REGEX) ? "✅ 合法" : "❌ 不合法");
        }
    }
}
