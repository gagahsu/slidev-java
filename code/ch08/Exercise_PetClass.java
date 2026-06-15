// Ch08 練習：完整 Pet 類別
// 設計含欄位與方法的完整類別，並練習用「防呆」寫法確保數值不會變成負數

class Pet {
    String name;
    String type;
    int hunger;

    void feed(int amount) {
        hunger -= amount;
        // 防呆寫法：避免飢餓值變成負數，之後遇到分數、庫存等欄位也常用同樣邏輯
        if (hunger < 0) hunger = 0;
    }

    void displayInfo() {
        System.out.println(name + "（" + type + "）的飢餓值：" + hunger);
    }
}

public class Exercise_PetClass {
    public static void main(String[] args) {
        Pet p1 = new Pet();
        p1.name = "小白";
        p1.type = "狗";
        p1.hunger = 50;

        p1.feed(30);
        p1.displayInfo(); // 小白（狗）的飢餓值：20
    }
}
