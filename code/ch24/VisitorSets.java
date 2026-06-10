// 練習 3-1：去除重複的訪客名單
// HashSet / LinkedHashSet / TreeSet 的順序差異

import java.util.*;

public class VisitorSets {

    public static void main(String[] args) {
        String[] visitors = {"Alice", "Bob", "Alice", "Charlie", "Bob", "Alice"};

        Set<String> hs = new HashSet<>(Arrays.asList(visitors));
        System.out.println("HashSet（" + hs.size() + " 人）：" + hs);

        Set<String> lhs = new LinkedHashSet<>(Arrays.asList(visitors));
        System.out.println("LinkedHashSet：" + lhs);

        Set<String> ts = new TreeSet<>(Arrays.asList(visitors));
        System.out.println("TreeSet：" + ts);
    }
}
