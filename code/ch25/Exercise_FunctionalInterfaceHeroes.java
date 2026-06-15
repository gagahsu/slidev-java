// Ch25 練習：用函數式介面整理鬼殺隊名單
// 練習 Predicate（判斷）、Function（轉換）、Consumer（消費）三大函數式介面的組合使用

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Exercise_FunctionalInterfaceHeroes {

    public static void main(String[] args) {
        List<String> heroes = List.of("炭治郎", "禰豆子", "善逸", "伊之助", "蜜璃");

        // 1. Predicate<String>：判斷名字長度是否 >= 3，呼叫方式為 .test(...)
        Predicate<String> isLongName = name -> name.length() >= 3;

        // 2. Function<String, Integer>：將名字轉成它的長度，呼叫方式為 .apply(...)
        Function<String, Integer> nameLength = name -> name.length();

        // 3. Consumer<String>：印出「隊員：」+ 名字，呼叫方式為 .accept(...)
        Consumer<String> printHero = name -> System.out.println("隊員：" + name);

        // 4. 先用 Predicate 判斷，成立就用 Consumer 印出
        for (String name : heroes) {
            if (isLongName.test(name)) {
                printHero.accept(name);
            }
        }

        // 最後用 Function 印出「禰豆子」的名字長度
        System.out.println("禰豆子的名字長度：" + nameLength.apply("禰豆子"));
    }
}
