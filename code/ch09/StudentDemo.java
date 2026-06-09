class Student {
    private String name;
    private int score;

    // 無參數建構子：委託給有參數建構子
    public Student() {
        this("未知", 0);
    }

    // 有參數建構子
    public Student(String name, int score) {
        this.name = name;
        setScore(score); // 透過 setter 確保驗證邏輯
    }

    // Getter / Setter（JavaBean 慣例）
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getScore() { return score; }
    public void setScore(int score) {
        if (score >= 0 && score <= 100) {
            this.score = score;
        } else {
            System.out.println("分數不合法（需介於 0~100）：" + score);
        }
    }

    // static 方法：不需要物件就能呼叫
    public static boolean isPass(int score) {
        return score >= 60;
    }

    @Override
    public String toString() {
        return name + "（" + score + " 分，" + (isPass(score) ? "及格" : "不及格") + "）";
    }
}

public class StudentDemo {
    public static void main(String[] args) {
        Student s1 = new Student();           // 使用無參數建構子
        Student s2 = new Student("小明", 85);
        Student s3 = new Student("小華", 55);
        Student s4 = new Student("小美", 110); // 非法分數，警告

        System.out.println(s1);  // 未知（0 分，不及格）
        System.out.println(s2);  // 小明（85 分，及格）
        System.out.println(s3);  // 小華（55 分，不及格）

        System.out.println("75 分是否及格：" + Student.isPass(75)); // true
        System.out.println("55 分是否及格：" + Student.isPass(55)); // false
    }
}
