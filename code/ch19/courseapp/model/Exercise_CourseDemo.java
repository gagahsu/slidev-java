// Ch19 練習 2：建立第一個套件
// 建立 Course 物件並呼叫 showInfo()，驗證套件可正常運作

package courseapp.model;

public class Exercise_CourseDemo {
    public static void main(String[] args) {
        Course course = new Course("Java 程式設計", 3);
        course.showInfo();
        // 預期輸出：課程：Java 程式設計，學分：3
    }
}
