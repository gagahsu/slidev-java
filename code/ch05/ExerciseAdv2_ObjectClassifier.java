// Ch05 進階練習：分類任意物件
// 用 instanceof Pattern Matching（JDK 16 正式特性）依型別與內容分類物件
// 註：switch 的 Pattern Matching（case Type t when ...）在 JDK 17 仍是預覽功能，
//     要到 JDK 21 才正式定案，因此這裡改寫成 if/else + instanceof，JDK 17 可直接編譯

public class ExerciseAdv2_ObjectClassifier {

    static String classify(Object o) {
        if (o == null) {
            return "null 值";
        } else if (o instanceof Integer i && i >= 0) {
            return "正整數或零：" + i;
        } else if (o instanceof Integer i) {
            return "負整數：" + i;
        } else if (o instanceof String s && s.isEmpty()) {
            return "空字串";
        } else if (o instanceof String s) {
            return "字串：" + s;
        } else {
            return "其他型別";
        }
    }

    public static void main(String[] args) {
        System.out.println(classify(-5));      // 負整數：-5
        System.out.println(classify(0));       // 正整數或零：0
        System.out.println(classify(""));      // 空字串
        System.out.println(classify("Java"));  // 字串：Java
        System.out.println(classify(null));    // null 值
        System.out.println(classify(3.14));    // 其他型別
    }
}
