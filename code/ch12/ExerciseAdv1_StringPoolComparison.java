// Ch12 練習：字串池與 == 判斷
// 認證模擬題（OCA/OCP 經典題型）：驗證字串池 (String Pool) 與 new 建立新物件的差異

public class ExerciseAdv1_StringPoolComparison {
    public static void main(String[] args) {
        String a = "鬼殺隊";
        String b = "鬼殺隊";
        String c = new String("鬼殺隊");
        String d = c;

        // a == b：兩個都是雙引號宣告，會從字串池取得同一份「鬼殺隊」，所以 true
        System.out.println("a == b ? " + (a == b));   // true

        // a == c：c 是用 new 建立的新物件，位址跟字串池裡的 a 不同，所以 false
        System.out.println("a == c ? " + (a == c));   // false

        // c == d：d = c 直接複製了同一個位址，所以 true
        System.out.println("c == d ? " + (c == d));   // true

        // 結論：true false true（正確答案 B）
        // 業界實務：只要其中一個是用 new 建立的，== 結果就很容易出乎意料，
        // 比較字串內容請一律使用 equals()
        System.out.println("a.equals(c) ? " + a.equals(c)); // true（內容相同）
    }
}
