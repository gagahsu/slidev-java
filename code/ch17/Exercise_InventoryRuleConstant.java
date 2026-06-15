// Ch17 練習：商品分類常數
// 介面常數 LOW_STOCK_THRESHOLD 統一管理庫存警示門檻，Product 實作 checkStock()

interface InventoryRule {
    int LOW_STOCK_THRESHOLD = 10; // 介面成員變數預設 public static final

    void checkStock(int quantity);
}

class Product implements InventoryRule {
    @Override
    public void checkStock(int quantity) {
        // 業界常用寫法：直接使用介面常數，不需寫 InventoryRule.LOW_STOCK_THRESHOLD
        if (quantity < LOW_STOCK_THRESHOLD) {
            System.out.println("庫存不足，請補貨");
        } else {
            System.out.println("庫存充足");
        }
    }
}

public class Exercise_InventoryRuleConstant {
    public static void main(String[] args) {
        Product product = new Product();
        product.checkStock(5);  // 庫存不足，請補貨
        product.checkStock(20); // 庫存充足
    }
}
