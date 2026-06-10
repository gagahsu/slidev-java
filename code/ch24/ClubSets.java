// 練習 3-2：社團成員聯集、交集與差集
// Set 的 addAll / retainAll / removeAll

import java.util.*;

public class ClubSets {

    public static void main(String[] args) {
        Set<String> basketball = new HashSet<>(List.of("小明", "小華", "小美", "阿強"));
        Set<String> boardgame = new HashSet<>(List.of("小華", "阿強", "阿傑", "小芳"));

        // 聯集：A ∪ B
        Set<String> union = new HashSet<>(basketball);
        union.addAll(boardgame);
        System.out.println("聯集：" + union);

        // 交集：A ∩ B
        Set<String> inter = new HashSet<>(basketball);
        inter.retainAll(boardgame);
        System.out.println("交集：" + inter);

        // 差集：只在籃球社、不在桌遊社
        Set<String> diff = new HashSet<>(basketball);
        diff.removeAll(boardgame);
        System.out.println("差集：" + diff);
    }
}
