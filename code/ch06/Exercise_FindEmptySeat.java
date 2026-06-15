// Ch06-adv 自學練習一：跳出雙層迴圈找座位
// 練習迴圈標籤：break search 直接結束整個雙層迴圈，找到第一個空位就停止

public class Exercise_FindEmptySeat {
    public static void main(String[] args) {
        int[][] seats = {
            {1, 1, 0, 1, 1},
            {1, 1, 1, 1, 0},
            {0, 1, 1, 1, 1}
        };

        search:
        for (int row = 0; row < seats.length; row++) {
            for (int col = 0; col < seats[row].length; col++) {
                if (seats[row][col] == 0) {
                    System.out.println("找到空位：(" + row + ", " + col + ")");
                    break search; // 找到第一個空位就整個結束，不再檢查剩下的座位
                }
            }
        }
    }
}
