// Ch08 練習：水果籃物件陣列
// 練習「宣告物件陣列 -> 逐一 new -> 設定欄位 -> for-each 遍歷」這套標準流程，並加上累加計算

class Fruit {
    String name;
    int price;

    void displayInfo() {
        System.out.println(name + "：" + price);
    }
}

public class Exercise_FruitBasketArray {
    public static void main(String[] args) {
        Fruit[] fruits = new Fruit[3];

        fruits[0] = new Fruit();
        fruits[0].name = "蘋果";
        fruits[0].price = 35;

        fruits[1] = new Fruit();
        fruits[1].name = "香蕉";
        fruits[1].price = 20;

        fruits[2] = new Fruit();
        fruits[2].name = "橘子";
        fruits[2].price = 25;

        // 注意：new Fruit[3] 只是建立陣列容器，每個位置一開始都是 null，
        // 若忘記對某個位置呼叫 new Fruit() 就直接存取，會拋出 NullPointerException
        int total = 0;
        for (Fruit f : fruits) {
            f.displayInfo();
            total += f.price;
        }
        System.out.println("總價：" + total); // 80
    }
}
