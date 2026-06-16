// Ch24 練習 5-1：樂透號碼產生器
// 練習 Collections.shuffle()、subList()、Collections.sort()、max()、min()

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Exercise6_LotteryGenerator {

    public static void main(String[] args) {
        // 1. 建立 1 ~ 49 的號碼清單
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 49; i++) {
            numbers.add(i);
        }

        // 2. 用 Collections.shuffle() 打亂順序
        Collections.shuffle(numbers);

        // 3. 取出前 6 個並排序（subList 是「分身」，複製一份才不影響 numbers）
        List<Integer> result = new ArrayList<>(numbers.subList(0, 6));
        Collections.sort(result);
        System.out.println("本期樂透號碼：" + result);

        // 4. 印出原始 numbers 的最大值與最小值
        System.out.println("最大值：" + Collections.max(numbers));
        System.out.println("最小值：" + Collections.min(numbers));
    }
}
