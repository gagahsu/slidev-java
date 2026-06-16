// Ch06 練習：偶數加總與索引印出
// 練習傳統 for（需要索引）與 for-each（不需要索引）的選擇與寫法

public class Exercise1_EvenSumIndex {
    public static void main(String[] args) {
        int[] nums = {3, 8, 12, 7, 20, 5, 16};

        // 任務一：印出索引與值（需要索引，用傳統 for）
        for (int i = 0; i < nums.length; i++) {
            System.out.println(i + ": " + nums[i]);
        }

        // 任務二：偶數加總（不需要索引，用 for-each）
        int sum = 0;
        for (int n : nums) {
            if (n % 2 == 0) {
                sum += n;
            }
        }
        System.out.println("偶數總和：" + sum);
    }
}
