// 練習 5-2：選擇合適的集合類別
// 依情境選擇 ArrayList / LinkedList / HashSet / LinkedHashSet / TreeSet / HashMap

import java.util.*;

public class CollectionChooser {

    public static void main(String[] args) {
        // 1. 瀏覽器「上一頁」紀錄：頻繁從前後新增/移除 -> LinkedList（實作 Deque，O(1)）
        Deque<String> history = new LinkedList<>();
        history.addLast("首頁");
        history.addLast("商品列表");
        System.out.println("瀏覽紀錄：" + history);

        // 2. 學生學號：不可重複 + 需排序 -> TreeSet
        Set<String> studentIds = new TreeSet<>();
        studentIds.add("S003");
        studentIds.add("S001");
        studentIds.add("S002");
        System.out.println("學號（已排序）：" + studentIds);

        // 3. 身分證字號 -> 姓名：鍵值對應 + 快速查詢 -> HashMap
        Map<String, String> idToName = new HashMap<>();
        idToName.put("A123456789", "小明");
        System.out.println("查詢姓名：" + idToName.get("A123456789"));

        // 4. 最近瀏覽商品：不可重複 + 保留插入順序 -> LinkedHashSet
        Set<String> recentProducts = new LinkedHashSet<>();
        recentProducts.add("筆記型電腦");
        recentProducts.add("滑鼠");
        recentProducts.add("筆記型電腦"); // 重複，會被忽略
        System.out.println("最近瀏覽：" + recentProducts);
    }
}
