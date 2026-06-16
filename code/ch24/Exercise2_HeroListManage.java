// Ch24 練習 2-1：管理英雄名單
// 練習 List 的 add(index)、set(index)、remove(index) 與 Collections.sort()

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Exercise2_HeroListManage {

    public static void main(String[] args) {
        // new ArrayList<> 包裝 List.of，確保清單可修改
        List<String> m = new ArrayList<>(
            List.of("炭治郎", "禰豆子", "善逸", "伊之助", "蜜璃"));

        // 1. 在「善逸」前面插入「甘露寺」（善逸原本在索引 2）
        m.add(2, "甘露寺");
        System.out.println("插入後：" + m);

        // 2. 將「禰豆子」替換為「時透無一郎」（禰豆子在索引 1）
        m.set(1, "時透無一郎");
        System.out.println("替換後：" + m);

        // 3. 移除最後一個成員（size() - 1 是最安全的寫法）
        m.remove(m.size() - 1);
        System.out.println("移除後：" + m);

        // 4. 依字典順序排序後印出
        Collections.sort(m);
        System.out.println("排序後：" + m);
    }
}
