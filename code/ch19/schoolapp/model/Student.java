// Ch19 綜合練習：設計套件結構與存取控制
// Student 類別示範三種存取修飾詞的使用情境：
// - name      private   只能透過 getter/setter 存取（最私密）
// - grade     protected 保留給未來子類別（如「研究生」）直接存取
// - studentId public    學號本身就是公開識別碼

package schoolapp.model;

public class Student {
    private String name;
    protected int grade;
    public String studentId;

    public Student(String studentId, String name, int grade) {
        this.studentId = studentId;
        this.name = name;
        this.grade = grade;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getGrade() { return grade; }
    public void setGrade(int grade) { this.grade = grade; }
}
