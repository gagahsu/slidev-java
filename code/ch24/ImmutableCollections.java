// 練習 1-2：不可變集合的應用
// List.of() / List.copyOf() 與 UnsupportedOperationException

import java.util.*;

public class ImmutableCollections {

    public static void main(String[] args) {
        // 1. List.of() 建立不可變清單
        List<String> weekdays = List.of("一", "二", "三", "四", "五", "六", "日");

        // 2. 對 weekdays 呼叫 add()，捕捉例外
        try {
            weekdays.add("補假");
        } catch (UnsupportedOperationException e) {
            System.out.println("weekdays 不可變：" + e);
        }

        // 3. 可變的 ArrayList，複製成不可變的 copy
        List<String> mutable = new ArrayList<>();
        mutable.add("A");
        mutable.add("B");
        List<String> copy = List.copyOf(mutable);

        // 4. 對 copy 呼叫 add()，同樣會拋出例外
        try {
            copy.add("C");
        } catch (UnsupportedOperationException e) {
            System.out.println("copy 不可變：" + e);
        }

        // mutable 仍可正常新增元素
        mutable.add("C");
        System.out.println("mutable: " + mutable);
        System.out.println("copy: " + copy);
    }
}
