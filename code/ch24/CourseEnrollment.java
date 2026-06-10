// 綜合練習：選課系統
// 整合 List、Set、Map 與 Collections 工具類別

import java.util.*;

public class CourseEnrollment {

    static void enroll(Map<String, List<String>> map, String course, String student) {
        if (!map.containsKey(course)) {
            map.put(course, new ArrayList<>());
        }
        map.get(course).add(student);
    }

    public static void main(String[] args) {
        Map<String, List<String>> courseEnrollment = new HashMap<>();

        enroll(courseEnrollment, "Java程式設計", "小明");
        enroll(courseEnrollment, "Java程式設計", "小華");
        enroll(courseEnrollment, "資料結構", "小美");
        enroll(courseEnrollment, "資料結構", "小明");
        enroll(courseEnrollment, "資料結構", "小華");
        enroll(courseEnrollment, "演算法", "小華");

        // 統計不重複學生數
        Set<String> allStudents = new HashSet<>();
        for (List<String> students : courseEnrollment.values()) {
            allStudents.addAll(students);
        }
        System.out.println("不重複學生數：" + allStudents.size());

        // 排序印出每門課名單 + 找出人數最多的課程
        String maxCourse = null;
        int maxCount = -1;
        for (var entry : courseEnrollment.entrySet()) {
            Collections.sort(entry.getValue());
            System.out.println(entry.getKey() + "：" + entry.getValue());
            if (entry.getValue().size() > maxCount) {
                maxCount = entry.getValue().size();
                maxCourse = entry.getKey();
            }
        }
        System.out.println("選課人數最多：" + maxCourse + "（" + maxCount + " 人）");
    }
}
