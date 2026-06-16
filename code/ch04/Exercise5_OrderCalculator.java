import java.util.Scanner;

// Ch04 練習：Scanner 與字串轉數值
// 模擬點餐小計算機：讀取餐點名稱、單價、數量字串，計算總金額

public class Exercise5_OrderCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("請輸入餐點名稱：");
        String name = scanner.nextLine();

        System.out.print("請輸入單價：");
        int price = scanner.nextInt();

        // 證照常考：nextInt() 之後接 nextLine() 要先消耗殘留的換行字元
        scanner.nextLine();

        System.out.print("請輸入數量：");
        String qtyStr = scanner.next();

        // 模擬從表單取得的文字資料，需先用 parseInt 轉成 int 才能計算
        int qty = Integer.parseInt(qtyStr);

        int total = price * qty;
        System.out.println(name + " x " + qty + " = " + total + " 元");

        scanner.close();
    }
}
