// 練習一：管理英雄名單
// ArrayList 基本操作：插入、替換、刪除、排序

import java.util.*;

public class HeroList {

    public static void main(String[] args) {
        // 使用 new ArrayList<>(List.of(...)) 建立可修改的清單
        List<String> members = new ArrayList<>(
            List.of("炭治郎", "禰豆子", "善逸", "伊之助", "蜜璃"));
        System.out.println("初始名單：" + members);

        // 1. 在「善逸」前面插入「甘露寺」（善逸原本在索引 2）
        members.add(2, "甘露寺");
        System.out.println("插入甘露寺後：" + members);

        // 2. 將「禰豆子」替換為「時透無一郎」（禰豆子在索引 1）
        members.set(1, "時透無一郎");
        System.out.println("替換禰豆子後：" + members);

        // 3. 移除最後一個成員
        members.remove(members.size() - 1);
        System.out.println("移除最後一個後：" + members);

        // 4. 依字典順序排序後印出
        Collections.sort(members);
        System.out.println("排序後：" + members);
    }
}
