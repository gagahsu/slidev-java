// 練習一：篩選與串接英雄名單
// Stream：filter → sorted → Collectors.joining

import java.util.*;
import java.util.stream.*;

public class HeroStream {

    public static void main(String[] args) {
        List<String> heroes = List.of(
            "炭治郎", "禰豆子", "善逸", "伊之助", "蜜璃", "甘露寺", "時透無一郎");

        // 篩選名字長度 >= 3、依字典排序、用「、」串接成一行
        String result = heroes.stream()
            .filter(name -> name.length() >= 3)
            .sorted()
            .collect(Collectors.joining("、"));

        // 中文依 Unicode 編碼排序：伊之助、時透無一郎、炭治郎、甘露寺、禰豆子
        System.out.println(result);
    }
}
