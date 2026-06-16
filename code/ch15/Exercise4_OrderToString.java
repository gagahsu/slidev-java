// Ch15 練習：Override Order 的 toString()
// 比較「未覆寫 toString()」與「覆寫 toString()」兩種輸出格式的差異

public class Exercise4_OrderToString {

    // 步驟一：未覆寫 toString()，觀察預設輸出格式
    static class OrderDefault {
        String orderId;
        String customer;
        int amount;

        OrderDefault(String orderId, String customer, int amount) {
            this.orderId = orderId;
            this.customer = customer;
            this.amount = amount;
        }
    }

    // 步驟二：覆寫 toString()
    static class Order {
        String orderId;
        String customer;
        int amount;

        Order(String orderId, String customer, int amount) {
            this.orderId = orderId;
            this.customer = customer;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return "訂單 " + orderId + "，客戶：" + customer + "，金額：" + amount;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 步驟一：未覆寫 toString() ===");
        OrderDefault d = new OrderDefault("O001", "古古", 1500);
        System.out.println(d);
        // → OrderDefault@xxxxxxxx（類別名稱@雜湊碼，不具可讀性）

        System.out.println("\n=== 步驟二：覆寫 toString() ===");
        Order order = new Order("O001", "古古", 1500);
        System.out.println(order);
        // → 訂單 O001，客戶：古古，金額：1500

        // println() 程式碼本身不需修改，println() 會自動呼叫 toString()
    }
}
