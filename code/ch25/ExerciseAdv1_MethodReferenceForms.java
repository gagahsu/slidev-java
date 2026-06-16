// Ch25 進階練習一：方法參考形式判斷
// 判斷三段程式碼各使用了哪一種方法參考：bound、unbound、建構子參考

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ExerciseAdv1_MethodReferenceForms {

    public static void main(String[] args) {
        List<String> names = List.of("炭治郎", "善逸", "伊之助");

        // (A) System.out 是「已經存在的物件」，:: 左邊是物件本身 -> bound
        names.forEach(System.out::println);

        // (B) String 是類別名稱，toUpperCase() 套用在 Stream 裡每個字串自己身上 -> unbound
        List<String> upper = names.stream()
            .map(String::toUpperCase)
            .toList();
        System.out.println(upper);

        // (C) LinkedList::new 是建構子，作為 Collectors.toCollection 的「容器工廠」 -> 建構子參考
        LinkedList<String> collected = names.stream()
            .collect(Collectors.toCollection(LinkedList::new));
        System.out.println(collected);
    }
}
