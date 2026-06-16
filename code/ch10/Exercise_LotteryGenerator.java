// Ch10 綜合練習一：樂透號碼產生器
// 模擬台灣大樂透（49 選 6）：從 1~49 隨機抽 6 個不重複號碼，排序後印出，再抽特別號
// 業界常用：List + contains() 檢查不重複；Collections.sort() 排序；String.format("%02d") 補零

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Exercise_LotteryGenerator {
    public static void main(String[] args) {
        Random rand = new Random();
        List<Integer> nums = new ArrayList<>();

        // 不斷抽號碼，直到抽到 6 個不重複的號碼
        while (nums.size() < 6) {
            int n = rand.nextInt(49) + 1; // [1, 49]
            if (!nums.contains(n)) {      // 檢查不重複
                nums.add(n);
            }
        }

        // 由小到大排序
        Collections.sort(nums);

        // 抽特別號：不能與前 6 個重複
        int special;
        do {
            special = rand.nextInt(49) + 1;
        } while (nums.contains(special));

        // 組合輸出字串，每個號碼補零為兩位數
        StringBuilder sb = new StringBuilder("大樂透號碼：");
        for (int i = 0; i < nums.size(); i++) {
            sb.append(String.format("%02d", nums.get(i)));
            if (i < nums.size() - 1) sb.append(" ");
        }
        System.out.println(sb.toString());
        System.out.println("特別號：" + String.format("%02d", special));
    }
}
