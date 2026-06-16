// Ch09 進階練習：計數器與 Singleton
// 結合 static 計數器與 Singleton 設計模式：
// 1. StudentTracker 類別加入 static totalCount 欄位，每次 new 自動累計
// 2. AcademyConfig Singleton 儲存學校名稱，確保全程只有一個實體
// 注意：類別命名為 StudentTracker / AcademyConfig，避免與 StudentCountAndSingleton.java
// 中的 StudentWithCount / SchoolConfig 撞名（Eclipse default package 全域唯一）

class StudentTracker {
    private String name;
    private int score;
    // static 欄位：全類別共用一份，記錄累計建立的物件數
    static int totalCount = 0;

    StudentTracker(String name, int score) {
        this.name = name;
        this.score = score;
        totalCount++; // 每次建立新物件時自動累計
    }

    @Override
    public String toString() {
        return name + "（" + score + " 分）";
    }
}

// Singleton：確保全程只有一個 AcademyConfig 實體
class AcademyConfig {
    private static AcademyConfig instance = null;
    private String schoolName;

    private AcademyConfig() {
        schoolName = "Java 程式設計學院";
    }

    // 業界常用：getInstance() 是取得 Singleton 的唯一入口
    public static AcademyConfig getInstance() {
        if (instance == null) {
            instance = new AcademyConfig();
        }
        return instance;
    }

    public String getSchoolName() { return schoolName; }
}

public class ExerciseAdv4_SingletonCounter {
    public static void main(String[] args) {
        // 建立多個 StudentTracker，觀察 totalCount 自動累計
        StudentTracker s1 = new StudentTracker("小明", 90);
        StudentTracker s2 = new StudentTracker("小華", 75);
        StudentTracker s3 = new StudentTracker("小美", 60);

        System.out.println(s1);
        System.out.println(s2);
        System.out.println(s3);
        // 驗證：totalCount 等於建立的物件數
        System.out.println("目前學生人數：" + StudentTracker.totalCount); // 3

        // 取得 Singleton 兩次，驗證是同一個物件
        AcademyConfig c1 = AcademyConfig.getInstance();
        AcademyConfig c2 = AcademyConfig.getInstance();
        System.out.println("學校名稱：" + c1.getSchoolName());
        System.out.println("c1 == c2：" + (c1 == c2)); // true（同一個物件）
    }
}
