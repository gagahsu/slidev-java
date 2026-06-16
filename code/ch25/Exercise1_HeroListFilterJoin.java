// Ch25 練習一：篩選與串接英雄名單
// Stream：filter（名字長度 >= 3）-> sorted -> Collectors.joining

import java.util.List;
import java.util.stream.Collectors;

public class Exercise1_HeroListFilterJoin {

    public static void main(String[] args) {
        List<String> heroes = List.of(
            "炭治郎", "禰豆子", "善逸", "伊之助", "蜜璃", "甘露寺", "時透無一郎");

        // 1. 篩選出名字長度 >= 3 個字的人
        // 2. 依字典順序排序
        // 3. 用 Collectors.joining("、") 串接後印出
        String result = heroes.stream()
            .filter(n -> n.length() >= 3)
            .sorted()
            .collect(Collectors.joining("、"));

        System.out.println(result);
        // 預期：炭治郎、禰豆子、伊之助、甘露寺、時透無一郎（字典順序）
    }
}
