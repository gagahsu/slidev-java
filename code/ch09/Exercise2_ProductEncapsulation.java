// Ch09 練習：設計符合封裝的 Product 類別
// 練習封裝原則：欄位 private + getter/setter，並遵守 JavaBean 命名慣例（boolean 欄位用 isXxx()）

public class Exercise2_ProductEncapsulation {

    static class Product {
        private String name;
        private int stock;
        private boolean discontinued;

        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public int getStock() { return stock; }
        // 驗證邏輯：stock 不可為負數，若為負數則不設定（維持原值）
        public void setStock(int stock) {
            if (stock >= 0) {
                this.stock = stock;
            }
        }

        // 業界常用：boolean 欄位的 getter 要寫成 isXxx()，
        // 框架（如 Spring、Jackson）才能正確辨識這是個 JavaBean 屬性
        public boolean isDiscontinued() { return discontinued; }
        public void setDiscontinued(boolean discontinued) {
            this.discontinued = discontinued;
        }
    }

    public static void main(String[] args) {
        Product p = new Product();
        p.setStock(-5); // 不合法，不會設定，stock 仍為預設值 0
        p.setStock(10); // 合法，stock 變成 10
        System.out.println(p.getStock()); // 10
    }
}
