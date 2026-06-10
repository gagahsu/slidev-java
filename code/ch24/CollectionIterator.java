// 練習 1-1：水果攤的 Collection 操作
// Collection 介面常用方法 + Iterator 安全移除元素

import java.util.*;

public class CollectionIterator {

    public static void main(String[] args) {
        List<String> fruits = new ArrayList<>(List.of("蘋果", "香蕉", "橘子", "葡萄"));

        // 1. 印出 size()
        System.out.println("數量：" + fruits.size());

        // 2. 用 contains() 檢查是否有「西瓜」
        System.out.println("有西瓜嗎？" + fruits.contains("西瓜"));

        // 3. 用 Iterator 遍歷並移除「香蕉」
        Iterator<String> it = fruits.iterator();
        while (it.hasNext()) {
            if (it.next().equals("香蕉")) {
                it.remove();
            }
        }

        // 4. 印出移除後的清單與 isEmpty()
        System.out.println("移除香蕉後：" + fruits);
        System.out.println("是否為空：" + fruits.isEmpty());
    }
}
