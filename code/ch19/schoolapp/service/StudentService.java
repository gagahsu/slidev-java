// Ch19 綜合練習：設計套件結構與存取控制
// StudentService 與 Student 不在同一套件，必須 import 才能使用

package schoolapp.service;

import schoolapp.model.Student;

public class StudentService {
    public void printInfo(Student s) {
        // s.studentId 可直接存取，因為是 public
        System.out.println("學號：" + s.studentId);
        // s.name 是 private，只能透過 getName() 取得，不能寫 s.name
        System.out.println("姓名：" + s.getName());
        // s.grade 是 protected，StudentService 不是 Student 的子類別也不同套件，
        // 一樣只能透過 getGrade() 存取，不能直接寫 s.grade
        System.out.println("年級：" + s.getGrade());
    }
}
