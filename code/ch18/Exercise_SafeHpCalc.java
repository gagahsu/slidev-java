// Ch18 練習：安全計算生命值
// 用三元運算子避免 Integer 為 null 時的 NullPointerException，
// 並驗證 Integer 快取範圍 (-128~127) 對 == 比較結果的影響

public class Exercise_SafeHpCalc {
    public static void main(String[] args) {
        Integer maxHp = 100;
        Integer currentDamage = null;

        // 安全寫法：先判斷 null，避免自動拆箱時拋出 NullPointerException
        int remainingHp = (currentDamage != null) ? maxHp - currentDamage : maxHp;
        System.out.println("剩餘生命值：" + remainingHp); // currentDamage 為 null，視為傷害 0

        // 證照常考：Integer 快取範圍是 -128 ~ 127
        Integer x = 100, y = 100;
        Integer x2 = 200, y2 = 200;

        System.out.println("100 == 100 → " + (x == y));   // true（在快取範圍內，同一個物件）
        System.out.println("200 == 200 → " + (x2 == y2)); // false（超出快取範圍，不同物件）
    }
}
