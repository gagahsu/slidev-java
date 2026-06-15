import java.util.Scanner;

// Ch05 練習二：月份轉季節（綜合練習）
// 先用 if 檢查月份範圍，再用傳統 switch 搭配 fall-through 將月份歸類為四季

public class Exercise_MonthToSeason {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("請輸入月份（1-12）：");
        int month = scanner.nextInt();

        if (month < 1 || month > 12) {
            System.out.println("輸入錯誤");
        } else {
            switch (month) {
                case 3: case 4: case 5:
                    System.out.println("春季");
                    break;
                case 6: case 7: case 8:
                    System.out.println("夏季");
                    break;
                case 9: case 10: case 11:
                    System.out.println("秋季");
                    break;
                // 12、1、2 月同屬冬季：case 12 寫在 case 1 之前，貫穿到 case 2
                case 12: case 1: case 2:
                    System.out.println("冬季");
                    break;
            }
        }

        scanner.close();
    }
}
