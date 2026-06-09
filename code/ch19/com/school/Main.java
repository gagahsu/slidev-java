package com.school;

import com.school.model.Student;
import com.school.service.StudentService;

public class Main {
    public static void main(String[] args) {
        Student s = new Student("S001", "炭治郎", 2);
        new StudentService().printInfo(s);
    }
}
