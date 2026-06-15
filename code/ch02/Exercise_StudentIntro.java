// Ch02 綜合練習：學生自我介紹程式
// 練習程式結構順序（import -> class -> main）、命名慣例（Pascal/camel Case）與三種註解寫法

/**
 * 學生自我介紹程式。
 */
public class Exercise_StudentIntro {
    public static void main(String[] args) {
        String studentName = "炭治郎"; // 姓名
        int studentAge = 16; // 年齡

        System.out.println("我是 " + studentName);
        System.out.printf("今年 %d 歲%n", studentAge);
    }
}
