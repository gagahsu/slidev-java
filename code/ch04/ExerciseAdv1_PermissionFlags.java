// Ch04 進階練習：位元邏輯與位移運算子
// 用位元運算子（&、|、^、<<）管理檔案權限旗標

public class ExerciseAdv1_PermissionFlags {
    public static void main(String[] args) {
        int permission1 = 0b0110; // 6，讀+寫
        int permission2 = 0b0101; // 5，讀+執行

        // & 逐位元比對，兩邊都是 1 才得 1 -> 共同擁有的權限（交集）
        System.out.println(permission1 & permission2); // 4 (0b0100，共同：讀)

        // | 任一邊是 1 即得 1 -> 合併後的權限（聯集）
        System.out.println(permission1 | permission2); // 7 (0b0111，合併：讀+寫+執行)

        // ^ 兩邊不同才得 1 -> 只有一邊擁有的權限（互斥或）
        System.out.println(permission1 ^ permission2); // 3 (0b0011，互斥：寫+執行)

        // 業界常用：左移 n 位 = 乘以 2 的 n 次方
        System.out.println(permission1 << 1); // 12 (0b1100)
    }
}
