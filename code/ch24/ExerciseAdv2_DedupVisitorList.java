// Ch24 練習 A-2：去除重複的訪客名單
// 比較 HashSet / LinkedHashSet / TreeSet 的順序差異

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public class ExerciseAdv2_DedupVisitorList {

    public static void main(String[] args) {
        String[] visitors = {"Alice", "Bob", "Alice", "Charlie", "Bob", "Alice"};

        // 1. HashSet：去重，但不保證順序
        Set<String> hs = new HashSet<>(Arrays.asList(visitors));
        System.out.println("HashSet（" + hs.size() + " 人）：" + hs);

        // 2. LinkedHashSet：去重，且維持「第一次出現」的順序
        Set<String> lhs = new LinkedHashSet<>(Arrays.asList(visitors));
        System.out.println("LinkedHashSet：" + lhs); // Alice, Bob, Charlie

        // 3. TreeSet：去重，依自然排序（字母升序）
        Set<String> ts = new TreeSet<>(Arrays.asList(visitors));
        System.out.println("TreeSet：" + ts); // Alice, Bob, Charlie（字母順序）

        // 重點整理：
        // - HashSet    → 最快（O(1)），但順序不可預測
        // - LinkedHashSet → 維持插入順序，稍微慢一點
        // - TreeSet    → 自動升序排列，但不允許 null，查詢 O(log n)
        System.out.println();
        System.out.println("LinkedHashSet 與 TreeSet 順序剛好一樣只是巧合，原理完全不同！");
    }
}
