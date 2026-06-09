/**
 * 練習一：個人資料卡
 * 使用 printf 整齊對齊輸出四種不同型態的個人資料
 */
public class PersonalCard {
    public static void main(String[] args) {
        String name = "炭治郎";
        int age = 16;
        double height = 165.5;
        boolean isStudent = true;

        System.out.printf("姓名：%s%n", name);
        System.out.printf("年齡：%3d%n", age);
        System.out.printf("身高：%.1f cm%n", height);
        System.out.printf("在學：%b%n", isStudent);
    }
}
