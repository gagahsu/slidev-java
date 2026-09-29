// Ch09 練習2：成績等第判斷（進階）
// 練習回傳型別 String + 條件判斷順序（由大到小）

public class Exercise2_GradeClassifier {

    public static String getGrade(int score) {
        if (score >= 90) {
            return "A";
        } else if (score >= 80) {
            return "B";
        } else if (score >= 70) {
            return "C";
        } else if (score >= 60) {
            return "D";
        } else {
            return "F";
        }
    }

    public static void main(String[] args) {
        String g = getGrade(85);
        System.out.println(g); // B

        System.out.println(getGrade(95)); // A
        System.out.println(getGrade(59)); // F
    }
}
