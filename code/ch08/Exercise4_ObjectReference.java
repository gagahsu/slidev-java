// Ch08 練習：物件參照行為
// 認證模擬題：考驗「物件賦值＝複製參照」與「== 比較記憶體位址而非欄位內容」

class Car {
    String brand;
    int speed;
}

public class Exercise4_ObjectReference {
    public static void main(String[] args) {
        Car c1 = new Car();
        c1.brand = "Toyota";
        c1.speed = 50;

        // c2 = c1：只複製參照（位址），c2 與 c1 指向同一個物件
        Car c2 = c1;
        c2.speed = 100;

        // 用 new 另外建立，即使欄位內容相同，也是不同物件（不同位址）
        Car c3 = new Car();
        c3.brand = "Toyota";
        c3.speed = 100;

        // c1.speed 也變成 100，因為 c2 與 c1 是同一個物件
        System.out.println(c1.speed);   // 100
        // c1 == c2：兩者指向同一塊記憶體，結果是 true
        System.out.println(c1 == c2);   // true
        // c1 == c3：c3 是用 new 另外建立的，位址不同，結果是 false
        // 業界常考：== 比的是「是否為同一個物件」，不是欄位內容是否相等
        System.out.println(c1 == c3);   // false
        // 正確答案：B（100 / true / false）
    }
}
