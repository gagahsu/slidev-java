// Ch24 練習 A-1：待辦事項佇列
// 使用 LinkedList 的雙端操作：addLast / addFirst / removeFirst

import java.util.LinkedList;

public class ExerciseAdv1_TodoQueue {

    public static void main(String[] args) {
        LinkedList<String> todos = new LinkedList<>();

        // 1. 用 addLast() 依序加入待辦事項（加到末尾）
        todos.addLast("買菜");
        todos.addLast("寫作業");
        todos.addLast("運動");
        System.out.println("初始佇列：" + todos);

        // 2. 用 addFirst() 將緊急任務「澆花」插到最前面
        todos.addFirst("澆花");
        System.out.println("插入急件後：" + todos);

        // 3. 用 removeFirst() 取出第一項（模擬完成任務）
        System.out.println("處理：" + todos.removeFirst()); // 澆花

        // 4. 印出剩餘待辦事項
        System.out.println("剩餘待辦：" + todos);

        // 複雜度補充說明：
        // LinkedList 的 addFirst / removeFirst 是 O(1)（不需搬移元素）
        // ArrayList 的 add(0, ...) / remove(0) 是 O(n)（需搬移後面所有元素）
        // → 若需要頻繁在頭端操作，LinkedList 比 ArrayList 更有效率
        System.out.println();
        System.out.println("複雜度對比：");
        System.out.println("  LinkedList.addFirst()  / removeFirst() → O(1)");
        System.out.println("  ArrayList.add(0, ...) / remove(0)      → O(n)");
    }
}
