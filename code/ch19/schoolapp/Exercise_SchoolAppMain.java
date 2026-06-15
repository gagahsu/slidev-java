// Ch19 綜合練習：設計套件結構與存取控制
// Main 與 Student、StudentService 都不在同一套件，兩者都要 import
// 完整走過「分套件 -> import -> 用存取修飾詞保護資料」的流程

package schoolapp;

import schoolapp.model.Student;
import schoolapp.service.StudentService;

public class Exercise_SchoolAppMain {
    public static void main(String[] args) {
        Student s = new Student("S001", "炭治郎", 2);
        new StudentService().printInfo(s);
        // 預期輸出：
        // 學號：S001
        // 姓名：炭治郎
        // 年級：2
    }
}
