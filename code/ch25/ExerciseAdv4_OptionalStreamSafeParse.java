// Ch25 進階練習四：Optional 串流綜合練習
// flatMap + Optional.stream() + Primitive Stream 的綜合應用

import java.util.List;
import java.util.Optional;

public class ExerciseAdv4_OptionalStreamSafeParse {

    // 1. 把可能拋出例外的解析動作，包成一個「可能有值、可能沒有」的 Optional，
    //    外面的人不用再寫 try-catch
    static Optional<Integer> safeParse(String s) {
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static void main(String[] args) {
        List<String> raw = List.of("85", "abc", "60", "", "92");

        // 2. Optional.stream()：有值的 Optional 變成「裝一個元素的 Stream」，
        //    空的 Optional 變成「空 Stream」，flatMap 接上去後，
        //    解析失敗的字串就自動「消失」，不會出現 null 或例外
        List<Integer> nums = raw.stream()
            .flatMap(s -> safeParse(s).stream())
            .toList();
        System.out.println(nums); // [85, 60, 92]

        // 3. 轉成數字模式（IntStream）後計算總和
        int total = nums.stream().mapToInt(Integer::intValue).sum();
        System.out.println(total); // 237
    }
}
