// Ch07 練習：第一個與最後一個
// 練習用 length - 1 取得最後一個元素的索引，不寫死數字

public class Exercise_FirstAndLast {
    public static void main(String[] args) {
        int[] prices = {120, 350, 80, 999, 60};

        int first = prices[0];
        // 證照常考：最後一個元素的索引是 length - 1，別寫成 prices[prices.length]
        int last = prices[prices.length - 1];

        System.out.println("第一個：" + first);
        System.out.println("最後一個：" + last);
        System.out.println("總和：" + (first + last));
    }
}
