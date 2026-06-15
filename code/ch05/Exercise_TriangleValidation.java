// Ch05 練習：判斷三角形是否合法
// 輸入三邊長，用 if-else 判斷是否能構成三角形，並用三元運算子找出最大邊

public class Exercise_TriangleValidation {
    public static void main(String[] args) {
        int a = 3, b = 4, c = 5;

        // 三角形成立條件：任意兩邊之和必須大於第三邊，三個條件須同時成立（&&）
        if (a + b > c && a + c > b && b + c > a) {
            System.out.println("可以構成三角形");
        } else {
            System.out.println("無法構成三角形");
        }

        // 巢狀三元運算子：先比較 a 和 b，再跟 c 比較
        // 證照常考：超過兩層的巢狀三元運算子會降低可讀性，建議改回 if-else
        int max = (a > b) ? (a > c ? a : c) : (b > c ? b : c);
        System.out.println("最大邊：" + max);
    }
}
