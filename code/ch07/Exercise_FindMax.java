// Ch07 綜合練習 1：找出最大值
// 練習「先假設第一個最強，再嘗試推翻它」的擂台賽邏輯，同時記錄最大值與索引

public class Exercise_FindMax {
    public static void main(String[] args) {
        int[] arr = {34, 17, 89, 45, 23, 67};

        // 先假設第一個元素是最大值（擂主）
        int maxVal = arr[0];
        int maxIdx = 0;

        // 從索引 1 開始走訪，讓後面的元素逐一挑戰擂主
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > maxVal) {
                maxVal = arr[i];
                maxIdx = i;
            }
        }

        // 業界慣例：找最大值不使用 Arrays.sort（sort 會破壞原陣列順序且只適合求值不求位置）
        // 證照常考：同時記錄 maxVal 和 maxIdx，才能印出「值與位置」
        System.out.println("最大值：" + maxVal + "，位於索引：" + maxIdx);
    }
}
