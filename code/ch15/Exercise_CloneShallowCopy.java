// Ch15 練習：clone() 與 finalize() 的取捨
// 驗證 Object.clone() 的「淺層複製」陷阱：物件型態欄位只會複製參照

public class Exercise_CloneShallowCopy {

    static class Pet {
        String name;
        Pet(String name) { this.name = name; }
    }

    // 淺層複製版：super.clone() 只複製參照，pet 欄位仍指向同一物件
    static class Owner implements Cloneable {
        Pet pet;

        @Override
        public Owner clone() {
            try {
                return (Owner) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError(e); // 不會發生，因為已實作 Cloneable
            }
        }
    }

    // 深層複製版：在 clone() 中手動 new 出新的 Pet，避免共用參照
    static class DeepOwner implements Cloneable {
        Pet pet;

        @Override
        public DeepOwner clone() {
            try {
                DeepOwner copy = (DeepOwner) super.clone();
                copy.pet = new Pet(this.pet.name); // 用「複製建構子」概念重建 Pet
                return copy;
            } catch (CloneNotSupportedException e) {
                throw new AssertionError(e);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== 淺層複製（super.clone()）===");
        Owner o1 = new Owner();
        o1.pet = new Pet("旺財");
        Owner o2 = o1.clone();
        o2.pet.name = "小白";
        System.out.println("o1.pet.name = " + o1.pet.name);
        // → 小白：o1.pet 與 o2.pet 指向同一個 Pet 物件，所以 o1 也被改到

        System.out.println("\n=== 深層複製（手動重建欄位）===");
        DeepOwner d1 = new DeepOwner();
        d1.pet = new Pet("旺財");
        DeepOwner d2 = d1.clone();
        d2.pet.name = "小白";
        System.out.println("d1.pet.name = " + d1.pet.name);
        // → 旺財：d1.pet 與 d2.pet 是不同的 Pet 物件，互不影響

        // 業界備註（Effective Java）：
        // clone()/Cloneable 機制因為「淺層複製」陷阱與例外處理麻煩，
        // 現代做法多改用「複製建構子」或「靜態工廠方法」明確重建每一層物件。
        // finalize() 在 Java 9 起已被標記為 deprecated，
        // 不應依賴它做資源釋放，應改用 try-with-resources 或 Cleaner。
    }
}
