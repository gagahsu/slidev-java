/**
 * 練習二：成績單格式化
 * 使用 static final 定義及格線，用 printf 輸出對齊的成績單
 */
public class GradeSheet {

    // 及格線常數：使用 static final 方便統一維護
    static final int PASS_SCORE = 60;

    public static void main(String[] args) {
        String name1 = "炭治郎"; int score1 = 95;
        String name2 = "禰豆子"; int score2 = 72;
        String name3 = "善逸";   int score3 = 58;

        boolean pass1 = score1 >= PASS_SCORE;
        boolean pass2 = score2 >= PASS_SCORE;
        boolean pass3 = score3 >= PASS_SCORE;

        System.out.println("===== 成績單 =====");
        System.out.printf("%-6s %4s  %s%n", "姓名", "分數", "是否及格");
        System.out.printf("%-6s %4d  %s%n", name1, score1, pass1 ? "✓" : "✗");
        System.out.printf("%-6s %4d  %s%n", name2, score2, pass2 ? "✓" : "✗");
        System.out.printf("%-6s %4d  %s%n", name3, score3, pass3 ? "✓" : "✗");
        System.out.println("==================");
        System.out.printf("及格線：%d 分%n", PASS_SCORE);
    }
}
