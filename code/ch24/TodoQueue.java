// 練習 2-2：待辦事項佇列
// LinkedList 的雙端操作（Deque）：addFirst / addLast / removeFirst

import java.util.LinkedList;

public class TodoQueue {

    public static void main(String[] args) {
        LinkedList<String> todos = new LinkedList<>();

        // 1. addLast 依序加入
        todos.addLast("買菜");
        todos.addLast("寫作業");
        todos.addLast("運動");

        // 2. addFirst 加到最前面
        todos.addFirst("澆花");
        System.out.println("待辦清單：" + todos);

        // 3. removeFirst 取出第一項
        System.out.println("處理：" + todos.removeFirst());

        // 4. 印出剩餘的待辦事項
        System.out.println("剩餘待辦：" + todos);

        // 說明：ArrayList 的 add(0, ...) / remove(0) 是 O(n)，
        // 因為要搬移其餘所有元素；LinkedList 的 addFirst/removeFirst 是 O(1)。
    }
}
