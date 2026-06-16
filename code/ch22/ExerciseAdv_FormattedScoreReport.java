// Ch22 練習：格式化成績單
// 練習 System.out.printf 的欄位對齊：靠左對齊、靠右對齊、固定小數位數

public class ExerciseAdv_FormattedScoreReport {

    record Student(String name, int score, double avg) {}

    public static void main(String[] args) {
        Student[] students = {
            new Student("Alice", 95, 88.5),
            new Student("Bob", 7, 72.333),
            new Student("Carol", 100, 91.0)
        };

        for (Student s : students) {
            // %-8s：字串靠左對齊，固定寬度 8（不足補空白）
            // %5d ：整數靠右對齊，固定寬度 5
            // %6.1f：浮點數固定寬度 6，小數點後 1 位（四捨五入）
            // %n  ：平台換行符，優於 \n（業界慣例：跨平台輸出建議用 %n）
            System.out.printf("%-8s %5d %6.1f%n",
                s.name(), s.score(), s.avg());
        }
    }
}
