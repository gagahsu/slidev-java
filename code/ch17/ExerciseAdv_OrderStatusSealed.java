// Ch17-adv 綜合練習：訂單狀態的密封設計
// sealed interface + record + instanceof Pattern Matching：整合 default 方法與 Sealed Interfaces
// 註：switch 的 Pattern Matching 在 JDK 17 仍是預覽功能，要到 JDK 21 才正式定案，
//     這裡改寫成 if/else + instanceof，JDK 17 可直接編譯

sealed interface OrderStatus permits Pending, Shipped, Cancelled {
    String describe();

    default void printStatus() {
        System.out.println("訂單狀態：" + describe());
    }
}

record Pending() implements OrderStatus {
    @Override
    public String describe() {
        return "待處理";
    }
}

record Shipped() implements OrderStatus {
    @Override
    public String describe() {
        return "已出貨";
    }
}

record Cancelled() implements OrderStatus {
    @Override
    public String describe() {
        return "已取消";
    }
}

public class ExerciseAdv_OrderStatusSealed {
    // 業界常用寫法：sealed + record + instanceof Pattern Matching，是 JDK 17 處理「有限狀態集合」的標準寫法
    static String getNextAction(OrderStatus status) {
        if (status instanceof Pending) {
            return "請出貨";
        } else if (status instanceof Shipped) {
            return "等待收貨";
        } else if (status instanceof Cancelled) {
            return "無需處理";
        }
        // sealed + permits 已窮舉所有型態，這裡只是滿足回傳值要求
        throw new IllegalStateException("未知的 OrderStatus 型態");
    }

    public static void main(String[] args) {
        OrderStatus[] statuses = { new Pending(), new Shipped(), new Cancelled() };

        for (OrderStatus status : statuses) {
            status.printStatus();
            System.out.println("下一步：" + getNextAction(status));
        }
    }
}
