// Ch19 練習 2：建立第一個套件
// Course 類別放在 courseapp.model 套件下，
// 對應目錄路徑 code/ch19/courseapp/model/，package 名稱與目錄路徑必須完全對應

package courseapp.model;

public class Course {
    private String courseName;
    private int credit;

    public Course(String courseName, int credit) {
        this.courseName = courseName;
        this.credit = credit;
    }

    public void showInfo() {
        System.out.println("課程：" + courseName + "，學分：" + credit);
    }
}
