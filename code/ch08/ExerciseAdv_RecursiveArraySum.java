// Ch08 自學綜合練習：遞迴計算陣列總和
// 結合「匿名陣列」與「遞迴」兩個主題：用遞迴走訪陣列索引並逐步累加總和
// Base Case：index == arr.length 時回傳 0；Recursive Case：arr[index] + recursiveSum(arr, index + 1)

public class ExerciseAdv_RecursiveArraySum {
    static int recursiveSum(int[] arr, int index) {
        if (index == arr.length) return 0; // Base Case：已走訪完所有元素
        return arr[index] + recursiveSum(arr, index + 1); // Recursive Case：往後一個索引縮小問題
    }

    public static void main(String[] args) {
        // 匿名陣列搭配遞迴呼叫，從 index = 0 開始
        System.out.println(recursiveSum(new int[]{1, 2, 3, 4, 5}, 0)); // 15
    }
}
