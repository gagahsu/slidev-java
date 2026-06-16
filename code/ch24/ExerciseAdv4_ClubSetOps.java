// Ch24 練習 B-1：社團成員聯集、交集與差集
// 練習 Set 的集合運算：addAll（聯集）、retainAll（交集）、removeAll（差集）

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExerciseAdv4_ClubSetOps {

    public static void main(String[] args) {
        Set<String> basketball = new HashSet<>(
            List.of("小明", "小華", "小美", "阿強"));
        Set<String> boardgame = new HashSet<>(
            List.of("小華", "阿強", "阿傑", "小芳"));

        // 聯集（A ∪ B）：兩社至少參加一個的所有人
        // 先複製一份再 addAll，避免改到原始資料
        Set<String> union = new HashSet<>(basketball);
        union.addAll(boardgame);
        System.out.println("聯集（至少參加一社）：" + union);

        // 交集（A ∩ B）：兩社都參加的成員
        Set<String> inter = new HashSet<>(basketball);
        inter.retainAll(boardgame);
        System.out.println("交集（兩社都參加）：" + inter);

        // 差集（A − B）：只參加籃球社、沒參加桌遊社的成員
        // removeAll 有方向性：basketball.removeAll(boardgame) ≠ boardgame.removeAll(basketball)
        Set<String> diff = new HashSet<>(basketball);
        diff.removeAll(boardgame);
        System.out.println("差集（只在籃球社）：" + diff);
    }
}
