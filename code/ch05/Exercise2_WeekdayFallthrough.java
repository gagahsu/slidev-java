import java.util.Scanner;

// Ch05 練習：星期幾的中文名稱
// 使用傳統 switch 搭配 fall-through，將 1~7 歸類為「平日」或「假日」

public class Exercise2_WeekdayFallthrough {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("請輸入星期幾（1-7）：");
        int day = scanner.nextInt();

        // fall-through：case 1~4 都沒有程式碼，會一路貫穿到 case 5 才執行
        switch (day) {
            case 1: case 2: case 3: case 4: case 5:
                System.out.println("平日，記得上班/上課");
                break;
            case 6: case 7:
                System.out.println("假日，好好休息");
                break;
            default:
                // 證照常考：務必加 default，避免不合法輸入時 switch 完全沒有輸出
                System.out.println("輸入錯誤");
        }

        scanner.close();
    }
}
