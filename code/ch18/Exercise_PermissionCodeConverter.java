// Ch18 綜合練習：權限碼轉換器
// 模擬 Linux chmod 概念：十進位權限碼 <-> 二進位字串互轉，
// 並用 Number 抽象父類別示範通用回傳型別

public class Exercise_PermissionCodeConverter {

    // 回傳型別宣告為 Number：內部回傳 Integer，因為 Integer 是 Number 的子類別
    static Number toScore(String binary) {
        // parseInt(binary, 2) 的 2 代表「輸入字串是二進位」，回傳十進位 int
        int decimal = Integer.parseInt(binary, 2);
        return Integer.valueOf(decimal);
    }

    public static void main(String[] args) {
        int input = 493; // 範例輸入（原題為 0~511 之間的十進位整數）

        // 第一步：十進位 → 二進位字串
        String binary = Integer.toBinaryString(input);
        System.out.println("二進位：" + binary);

        // 第二步：二進位字串 → Number（內部其實是 Integer），再取出 intValue()
        Number restored = toScore(binary);
        System.out.println("還原後：" + restored.intValue());

        // 預期輸出（輸入 493）：
        // 二進位：111101101
        // 還原後：493
    }
}
