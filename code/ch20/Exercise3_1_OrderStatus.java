// Ch20 練習3-1：訂單狀態 enum
// 設計 OrderStatus，依庫存與訂購數量回傳對應的狀態代碼與訊息

public class Exercise3_1_OrderStatus {

    enum OrderStatus {
        SUCCESS(200, "訂單成立"),
        OUT_OF_STOCK(400, "庫存不足"),
        INVALID_AMOUNT(401, "訂購數量不正確");

        private final int code;
        private final String message;

        OrderStatus(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

    static OrderStatus placeOrder(int stock, int amount) {
        if (amount <= 0) {
            return OrderStatus.INVALID_AMOUNT;
        } else if (amount > stock) {
            return OrderStatus.OUT_OF_STOCK;
        } else {
            return OrderStatus.SUCCESS;
        }
    }

    public static void main(String[] args) {
        int[][] orders = {{10, 0}, {10, 20}, {10, 5}};

        for (int[] order : orders) {
            int stock = order[0];
            int amount = order[1];
            OrderStatus result = placeOrder(stock, amount);
            System.out.println("庫存:" + stock + ", 訂購:" + amount
                + " -> [" + result.getCode() + "] " + result.getMessage());
        }
    }
}
