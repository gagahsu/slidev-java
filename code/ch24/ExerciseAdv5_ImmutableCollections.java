// Ch24 練習 C-1：不可變集合的應用
// 驗證 List.of() 與 List.copyOf() 的不可變性，以及複本與原本相互獨立

import java.util.ArrayList;
import java.util.List;

public class ExerciseAdv5_ImmutableCollections {

    public static void main(String[] args) {
        // 1. 用 List.of() 建立不可變的一週七天清單
        List<String> weekdays = List.of("一", "二", "三", "四", "五", "六", "日");
        System.out.println("weekdays：" + weekdays);

        // 2. 嘗試呼叫 add()，用 try-catch 捕捉例外
        try {
            weekdays.add("補假");
        } catch (UnsupportedOperationException e) {
            System.out.println("weekdays 不可變：" + e.getClass().getSimpleName());
        }

        // 3. 建立可變的 ArrayList，用 List.copyOf() 複製成不可變版本
        List<String> mutable = new ArrayList<>();
        mutable.add("A");
        mutable.add("B");
        List<String> copy = List.copyOf(mutable);

        // 4. 驗證 copy 不可修改
        try {
            copy.add("C");
        } catch (UnsupportedOperationException e) {
            System.out.println("copy 不可變：" + e.getClass().getSimpleName());
        }

        // 5. 驗證 mutable 仍可正常修改，且兩者互不影響
        mutable.add("C");
        System.out.println("mutable（可修改）: " + mutable); // [A, B, C]
        System.out.println("copy（快照）:      " + copy);    // [A, B]
        // copy 仍停留在「拍快照」當下的內容，不受 mutable 後續修改影響
    }
}
