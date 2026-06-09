package com.school.service;

import com.school.model.Student;

public class StudentService {
    public void printInfo(Student s) {
        System.out.println("學號：" + s.studentId);
        System.out.println("姓名：" + s.getName());
        System.out.println("年級：" + s.getGrade());
    }
}
