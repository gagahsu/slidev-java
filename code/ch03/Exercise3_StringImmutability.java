// Ch03 練習：字串比較與不可變性
// 練習字串池、== 與 equals() 的差異，以及 String 不可變性（concat 不會改變原字串）

public class Exercise3_StringImmutability {
    public static void main(String[] args) {
        String a = "Java17";
        String b = "Java17";
        String c = new String("Java17");

        System.out.println(a == b);      // true：字面值相同，字串池中是同一個物件
        System.out.println(a == c);      // false：c 用 new 建立，是不同物件（不同位址）
        System.out.println(a.equals(c)); // true：內容相同，比較內容一律用 equals()

        String d = a.concat(" 課程");
        System.out.println(a); // Java17：concat() 不會修改原字串
        System.out.println(d); // Java17 課程：d 是 concat() 產生的新字串
    }
}
