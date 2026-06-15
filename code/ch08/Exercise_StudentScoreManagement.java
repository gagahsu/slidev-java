// Ch08 綜合練習：學生成績管理
// 串連整章重點：建構子 + this（設定欄位）、物件陣列與 for-each 遍歷、
// 陣列參照（成績陣列以參照方式存入物件）

class Student {
    String name;
    int[] scores;

    Student(String name, int[] scores) {
        this.name = name;
        this.scores = scores;
    }

    double average() {
        int total = 0;
        for (int s : scores) total += s;
        return (double) total / scores.length;
    }

    void displayInfo() {
        System.out.println(name + " 平均：" + average());
    }
}

public class Exercise_StudentScoreManagement {
    public static void main(String[] args) {
        Student[] students = new Student[2];

        // 直接把陣列字面值（匿名陣列）當引數傳入建構子，
        // scores 欄位會參照到這個陣列，而不是複製一份資料
        students[0] = new Student("小明", new int[]{80, 90, 70});
        students[1] = new Student("小華", new int[]{60, 75, 85});

        for (Student s : students) {
            s.displayInfo();
        }
    }
}
