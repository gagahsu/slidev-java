// Ch09 綜合練習一：設計 Student 類別
// 練習建構子 + 封裝 + static 方法，結合本章所有重點
// 注意：類別命名為 ExStudentInfo，避免與同目錄 StudentDemo.java 中的 Student 撞名
// （Eclipse default package 下所有 top-level class 名稱必須全域唯一）

public class Exercise_StudentClass {

    // 使用 static 內部寫法，避免 ExStudentInfo 與其他檔案的同名 class 衝突
    static class ExStudentInfo {
        private String name;
        private int score;

        // 無參數建構子：委託給有參數建構子，傳入預設值
        public ExStudentInfo() {
            this("未知", 0);
        }

        // 有參數建構子：透過 setter 確保驗證邏輯統一在 setScore 裡
        public ExStudentInfo(String name, int score) {
            this.name = name;
            setScore(score); // 統一交給 setter 做驗證
        }

        // Getter / Setter（JavaBean 慣例）
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public int getScore() { return score; }
        // setter 加驗證：拒絕不合法的分數（需介於 0~100）
        public void setScore(int score) {
            if (score >= 0 && score <= 100) {
                this.score = score;
            } else {
                System.out.println("分數不合法（需介於 0~100）：" + score);
            }
        }

        // static 方法：不需要物件就能呼叫
        // 業界常用：工具性的判斷邏輯設計成 static，呼叫時用類別名稱而非物件
        public static boolean isPass(int score) {
            return score >= 60;
        }

        @Override
        public String toString() {
            return name + "（" + score + " 分，" + (isPass(score) ? "及格" : "不及格") + "）";
        }
    }

    public static void main(String[] args) {
        ExStudentInfo s1 = new ExStudentInfo();              // 使用無參數建構子
        ExStudentInfo s2 = new ExStudentInfo("小明", 85);
        ExStudentInfo s3 = new ExStudentInfo("小華", 55);
        ExStudentInfo s4 = new ExStudentInfo("小美", 110);   // 非法分數，印出警告

        System.out.println(s1);  // 未知（0 分，不及格）
        System.out.println(s2);  // 小明（85 分，及格）
        System.out.println(s3);  // 小華（55 分，不及格）

        // 用類別名稱呼叫 static 方法（不是物件名稱）
        System.out.println("75 分是否及格：" + ExStudentInfo.isPass(75)); // true
        System.out.println("55 分是否及格：" + ExStudentInfo.isPass(55)); // false
    }
}
