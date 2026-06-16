// Ch24 練習 1-1：水果攤的 Collection 操作
// 練習 Collection 基本方法：size()、contains()、Iterator 邊走邊刪、isEmpty()

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Exercise1_FruitStallCollection {

    public static void main(String[] args) {
        // new ArrayList<>(List.of(...))：建立可修改的清單（List.of 本身不可修改）
        List<String> fruits = new ArrayList<>(List.of("蘋果", "香蕉", "橘子", "葡萄"));

        // 1. 印出 size()
        System.out.println("水果數量：" + fruits.size()); // 4

        // 2. 用 contains() 檢查是否有「西瓜」
        System.out.println("有西瓜嗎？" + fruits.contains("西瓜")); // false

        // 3. 用 Iterator 遍歷並移除「香蕉」
        // 注意：不能用 for-each 邊跑邊刪，否則會拋出 ConcurrentModificationException
        Iterator<String> it = fruits.iterator();
        while (it.hasNext()) {
            if (it.next().equals("香蕉")) {
                it.remove(); // 移除「上一次 next() 拿到的元素」
            }
        }

        // 4. 印出移除後的清單與 isEmpty()
        System.out.println("移除後：" + fruits);         // [蘋果, 橘子, 葡萄]
        System.out.println("是否為空：" + fruits.isEmpty()); // false
    }
}
