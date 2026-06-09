// Student 類別加入靜態計數器
class StudentWithCount {
    private String name;
    private int score;
    static int totalCount = 0; // 所有物件共用

    public StudentWithCount(String name, int score) {
        this.name = name;
        this.score = (score >= 0 && score <= 100) ? score : 0;
        totalCount++; // 每次 new 就累計
    }

    public String getName() { return name; }
    public int getScore() { return score; }

    @Override
    public String toString() {
        return name + "（" + score + " 分）";
    }
}

// Singleton 設計模式
class SchoolConfig {
    private static SchoolConfig instance = null;
    private String schoolName;

    private SchoolConfig() {
        schoolName = "Java 程式設計學院";
    }

    public static SchoolConfig getInstance() {
        if (instance == null) {
            instance = new SchoolConfig();
        }
        return instance;
    }

    public String getSchoolName() { return schoolName; }
    public void setSchoolName(String name) { schoolName = name; }
}

public class StudentCountAndSingleton {
    public static void main(String[] args) {
        // 練習二（一）：靜態計數器
        StudentWithCount s1 = new StudentWithCount("小明", 90);
        StudentWithCount s2 = new StudentWithCount("小華", 75);
        StudentWithCount s3 = new StudentWithCount("小美", 60);

        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        System.out.println("目前學生人數：" + StudentWithCount.totalCount); // 3

        // 練習二（二）：Singleton 驗證
        SchoolConfig c1 = SchoolConfig.getInstance();
        SchoolConfig c2 = SchoolConfig.getInstance();

        System.out.println("學校名稱：" + c1.getSchoolName());
        System.out.println("c1 == c2：" + (c1 == c2)); // true（同一個物件）
    }
}
