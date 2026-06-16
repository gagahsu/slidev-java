// Ch05 練習一：成績等第轉換
// 輸入整數分數（0–100），用 if-else if-else 鏈輸出等第 A/B/C/D/F

public class Exercise_GradeLevel {
    public static void main(String[] args) {
        int score = 83;

        // 業界慣例：條件由嚴到寬排列，先判斷最高分段，避免低分段提前攔截
        String grade;
        if (score >= 90) {
            grade = "A";
        } else if (score >= 80) {
            grade = "B";
        } else if (score >= 70) {
            grade = "C";
        } else if (score >= 60) {
            grade = "D";
        } else {
            grade = "F";
        }

        System.out.println("等第：" + grade); // 預期輸出：等第：B
    }
}
