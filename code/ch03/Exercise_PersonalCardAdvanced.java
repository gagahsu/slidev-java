// Ch03 自學練習一：個人資料卡（進階格式）
// 練習 printf 的寬度（width）與精確度（precision）旗標，讓欄位對齊

public class Exercise_PersonalCardAdvanced {
    public static void main(String[] args) {
        String name = "炭治郎";
        int age = 16;
        double height = 165.5;
        boolean isStudent = true;

        System.out.printf("姓名：%s%n", name);
        System.out.printf("年齡：%3d%n", age);     // 寬度旗標：右對齊，至少 3 格
        System.out.printf("身高：%.1f cm%n", height); // 精確度旗標：取 1 位小數
        System.out.printf("在學：%b%n", isStudent);
    }
}
