// Ch12 練習：字元陣列轉字串
// 用 String(char[]) 建構方法把字元陣列轉成字串，再用 String(String) 複製一份，並比較 ==

public class Exercise2_CharArrayToString {
    public static void main(String[] args) {
        char[] name = {'禰', '豆', '子'};

        // String(char[] data)：由字元陣列組成字串
        String s1 = new String(name);
        // String(String original)：建立副本（新位址）
        String s2 = new String(s1);

        System.out.println("s1 = " + s1);
        System.out.println("s2 = " + s2);

        // s1、s2 內容相同，但都是用 new 建立的物件，位址不同，所以 == 為 false
        // 業界常說「比較字串內容要用 equals，不要用 =="，這題就是典型案例
        System.out.println("s1 == s2 ? " + (s1 == s2));
        System.out.println("s1.equals(s2) ? " + s1.equals(s2));
    }
}
