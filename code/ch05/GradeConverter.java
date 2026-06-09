import java.util.Scanner;

/**
 * 練習一：成績等第轉換
 * 接收整數分數（0–100），使用 if-else if-else 鏈輸出對應等第
 * 90~100 → A、80~89 → B、70~79 → C、60~69 → D、0~59 → F
 */
public class GradeConverter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("請輸入分數（0–100）：");
        int score = scanner.nextInt();

        // 條件由高到低，確保只會進入一個分支
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

        System.out.println("等第：" + grade);
        scanner.close();
    }
}
