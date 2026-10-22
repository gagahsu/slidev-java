// Ch18 練習二：判斷字串是否為合法整數
// 利用 Ch13 的正規表達式判斷：負號可有可無（-?），後面至少一個數字（\d+）

public class Exercise2_IsInteger {

    static boolean isInteger(String s) {
        return s.matches("-?\\d+");
    }

    public static void main(String[] args) {
        System.out.println("\"123\" → " + isInteger("123"));    // true
        System.out.println("\"-45\" → " + isInteger("-45"));    // true
        System.out.println("\"12.3\" → " + isInteger("12.3")); // false
        System.out.println("\"abc\" → " + isInteger("abc"));   // false
    }
}
