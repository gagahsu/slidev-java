// Ch16-adv 練習2：訂單處理流程
// Template Method Pattern：final void process() 固定流程，抽象方法交由子類別實作細節

abstract class OrderProcess {
    abstract void validate();
    abstract void pay();
    abstract void ship();

    // 業界常用寫法：流程骨架用 final 鎖住，子類別不可 override 執行順序
    final void process() {
        validate();
        pay();
        ship();
    }
}

class OnlineOrder extends OrderProcess {
    @Override
    void validate() {
        System.out.println("檢查線上訂單");
    }

    @Override
    void pay() {
        System.out.println("信用卡付款");
    }

    @Override
    void ship() {
        System.out.println("宅配出貨");
    }
}

public class ExerciseAdv2_OrderProcessTemplate {
    public static void main(String[] args) {
        new OnlineOrder().process();
        // 檢查線上訂單 → 信用卡付款 → 宅配出貨
    }
}
