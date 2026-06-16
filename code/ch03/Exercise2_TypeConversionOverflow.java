// Ch03 練習：型態轉換與溢位
// 觀察縮小轉換的截斷、byte 溢位、以及字面值後綴 L 對運算的影響

public class Exercise2_TypeConversionOverflow {
    public static void main(String[] args) {
        double price = 99.99;
        int rounded = (int) price; // 縮小轉換：直接截斷小數，不是四捨五入
        System.out.println("rounded = " + rounded); // 99

        int big = 130;
        byte small = (byte) big; // 證照常考：130 超出 byte 範圍(-128~127)，溢位後變成 130 - 256 = -126
        System.out.println("small = " + small); // -126

        int score = 100;
        long total = score * 1_000_000_000L; // score 因運算式中有 long 而自動 widening，避免在 int 階段溢位
        System.out.println("total = " + total); // 100000000000
    }
}
