// Ch08 自學練習一：陣列總和與最大值
// 延伸匿名陣列的用法：建立陣列的同時直接傳入方法，不需先宣告暫存變數

public class ExerciseAdv_ArraySumAndMax {
    static int sum(int[] arr) {
        int total = 0;
        for (int n : arr) total += n;
        return total;
    }

    static int max(int[] arr) {
        // 用第一個元素當初始最大值，避免「初始值該設多少」的困擾
        // （若直接設成 0，遇到全部都是負數的陣列就會出錯）
        int m = arr[0];
        for (int n : arr) if (n > m) m = n;
        return m;
    }

    public static void main(String[] args) {
        // 匿名陣列：建立後立即傳入方法，用完即丟
        System.out.println(sum(new int[]{4, 8, 15, 16, 23, 42})); // 108
        System.out.println(max(new int[]{4, 8, 15, 16, 23, 42})); // 42
    }
}
