// Ch24 練習 3-1：清點訪客名單
// 練習 HashSet 的自動去重、contains()、remove()

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Exercise3_VisitorListCheck {

    public static void main(String[] args) {
        String[] visitors = {"Alice", "Bob", "Alice", "Charlie", "Bob", "Alice"};

        // 1. 放入 HashSet，重複姓名自動被忽略
        Set<String> uniqueVisitors = new HashSet<>(Arrays.asList(visitors));
        System.out.println("不重複訪客數：" + uniqueVisitors.size()); // 3

        // 2. 用 contains() 檢查
        System.out.println("有 Alice？" + uniqueVisitors.contains("Alice")); // true
        System.out.println("有 David？" + uniqueVisitors.contains("David")); // false

        // 3. 移除「Bob」並印出結果
        uniqueVisitors.remove("Bob");
        System.out.println("移除 Bob 後：" + uniqueVisitors);
        // 注意：HashSet 輸出順序不保證，這是正常行為
    }
}
