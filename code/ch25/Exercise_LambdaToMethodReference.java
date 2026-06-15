// Ch25 練習：把 Lambda 改寫成方法參考
// 把三段 Lambda 改寫成對應的方法參考形式（ClassName::staticMethod、obj::instanceMethod、ClassName::instanceMethod）

import java.util.List;

public class Exercise_LambdaToMethodReference {

    public static void main(String[] args) {
        List<String> heroes = List.of("炭治郎", "禰豆子", "善逸");

        // (A) 原始寫法：heroes.forEach(name -> System.out.println(name));
        // System.out 是「已經存在的物件」 → obj::instanceMethod
        heroes.forEach(System.out::println);

        // (B) 原始寫法：heroes.stream().map(name -> name.length());
        // name 自己呼叫 length() → ClassName::instanceMethod
        heroes.stream().map(String::length).forEach(System.out::println);

        // (C) 原始寫法：heroes.stream().map(name -> Integer.valueOf(name.hashCode()));
        // 一行 lambda 其實做了兩件事（先 hashCode() 再 valueOf()），
        // 方法參考一次只能轉達「一件事」，所以拆成兩個 map：
        // 第一步 String::hashCode 是 ClassName::instanceMethod
        // 第二步 Integer::valueOf 是 ClassName::staticMethod
        heroes.stream()
              .map(String::hashCode)   // (C-1) ClassName::instanceMethod
              .map(Integer::valueOf)   // (C-2) ClassName::staticMethod
              .forEach(System.out::println);
    }
}
