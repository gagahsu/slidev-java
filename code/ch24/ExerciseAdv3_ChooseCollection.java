// Ch24 練習 A-3：選擇合適的集合類別
// 根據情境需求，選擇最合適的集合類別並說明理由

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class ExerciseAdv3_ChooseCollection {

    public static void main(String[] args) {
        // 情境 1：瀏覽器「上一頁」紀錄，需要頻繁從前後新增 / 移除
        // → LinkedList（實作 Deque，兩端操作 O(1)）
        Deque<String> history = new LinkedList<>();
        history.addLast("https://example.com");
        history.addLast("https://java.com");
        history.addFirst("https://google.com"); // 緊急插到最前
        System.out.println("1. 瀏覽紀錄（LinkedList）：" + history);

        // 情境 2：學生學號，不可重複，需依學號排序輸出
        // → TreeSet（天生不重複且自動依自然排序）
        Set<String> studentIds = new TreeSet<>();
        studentIds.add("S003");
        studentIds.add("S001");
        studentIds.add("S002");
        studentIds.add("S001"); // 重複，自動忽略
        System.out.println("2. 學號清單（TreeSet，自動排序）：" + studentIds);

        // 情境 3：身分證字號 → 姓名 對照表，需要快速查詢
        // → HashMap（鍵值對應，查詢 O(1)）
        Map<String, String> idToName = new HashMap<>();
        idToName.put("A123456789", "炭治郎");
        idToName.put("B987654321", "善逸");
        System.out.println("3. 身分證查詢（HashMap）：" + idToName.get("A123456789"));

        // 情境 4：最近瀏覽的商品名稱，不可重複，要保留瀏覽順序
        // → LinkedHashSet（去重 + 維持插入順序）
        Set<String> recentProducts = new LinkedHashSet<>();
        recentProducts.add("鬼滅劍");
        recentProducts.add("護符");
        recentProducts.add("鬼滅劍"); // 重複，忽略
        recentProducts.add("呼吸書");
        System.out.println("4. 最近瀏覽（LinkedHashSet）：" + recentProducts);
    }
}
