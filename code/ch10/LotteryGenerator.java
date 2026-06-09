import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class LotteryGenerator {
    public static void main(String[] args) {
        Random rand = new Random();

        // 抽出 6 個不重複的號碼（1~49）
        List<Integer> nums = new ArrayList<>();
        while (nums.size() < 6) {
            int n = rand.nextInt(49) + 1;
            if (!nums.contains(n)) {
                nums.add(n);
            }
        }
        Collections.sort(nums);

        // 抽出特別號（不可與前 6 個重複）
        int bonus;
        do {
            bonus = rand.nextInt(49) + 1;
        } while (nums.contains(bonus));

        // 輸出結果（補零兩位數）
        System.out.print("大樂透號碼：");
        for (int n : nums) {
            System.out.printf("%02d ", n);
        }
        System.out.println();
        System.out.printf("特別號：%02d%n", bonus);
    }
}
