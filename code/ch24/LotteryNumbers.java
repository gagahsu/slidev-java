// 練習 5-1：樂透號碼產生器
// Collections.shuffle / sort / max / min

import java.util.*;

public class LotteryNumbers {

    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 49; i++) {
            numbers.add(i);
        }

        Collections.shuffle(numbers);
        List<Integer> result = new ArrayList<>(numbers.subList(0, 6));
        Collections.sort(result);
        System.out.println("本期樂透號碼：" + result);

        System.out.println("最大值：" + Collections.max(numbers));
        System.out.println("最小值：" + Collections.min(numbers));
    }
}
