// Ch18 練習二：判斷字串是否為合法整數
// 利用 Integer.parseInt() 搭配 try-catch 判斷字串是否可轉為整數

public class Exercise2_IsInteger {

    static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println("\"123\" → " + isInteger("123"));    // true
        System.out.println("\"-45\" → " + isInteger("-45"));    // true
        System.out.println("\"12.3\" → " + isInteger("12.3")); // false
        System.out.println("\"abc\" → " + isInteger("abc"));   // false
    }
}
