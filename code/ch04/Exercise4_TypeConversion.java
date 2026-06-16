// Ch04 練習：型態轉換練習
// 練習自動型態提升（byte + byte -> int）與強制轉型（double -> int、int -> char）

public class Exercise4_TypeConversion {
    public static void main(String[] args) {
        // 1. byte + byte 運算結果會自動提升為 int
        byte x = 100, y = 50;
        int sum = x + y; // 自動提升為 int，結果 150
        System.out.println(sum);

        // 2. double 轉 int 是強制轉型，直接截去小數，不會四捨五入
        double price = 99.99;
        int rounded = (int) price; // 結果 99（不是 100）
        System.out.println(rounded);

        // 3. int 轉 char 會依照 ASCII／Unicode 對照表轉換
        int code = 97;
        char ch = (char) code; // 97 對應到小寫字母 'a'
        System.out.println(ch);
    }
}
