// Ch14 自學練習一：Sealed Classes 與 Records
// sealed interface 限制誰能實作，搭配 record 快速建立資料類別，
// 用 instanceof Pattern Matching 窮舉所有子型態
// 註：switch 的 Pattern Matching 在 JDK 17 仍是預覽功能，要到 JDK 21 才正式定案，
//     這裡改寫成 if/else + instanceof，JDK 17 可直接編譯
//
// 注意：投影片範例用 Vehicle/Car/Bike 命名，這裡改名為
// Transport/Sedan/Bicycle，避免與本資料夾其他練習的 Vehicle、Car 類別重複。

sealed interface Transport permits Sedan, Bicycle { }

record Sedan(String plate, int wheels) implements Transport { }
record Bicycle(String brand, int wheels) implements Transport { }

public class Exercise_SealedTransport {

    static String describe(Transport t) {
        if (t instanceof Sedan c) {
            return "汽車 " + c.plate() + "，" + c.wheels() + " 輪";
        } else if (t instanceof Bicycle b) {
            return b.brand() + " 腳踏車，" + b.wheels() + " 輪";
        }
        // sealed 讓編譯器知道 Sedan、Bicycle 已是全部可能，這裡只是滿足回傳值要求
        throw new IllegalStateException("未知的 Transport 型態");
    }

    public static void main(String[] args) {
        Transport t1 = new Sedan("ABC-1234", 4);
        Transport t2 = new Bicycle("Giant", 2);

        System.out.println(describe(t1));
        System.out.println(describe(t2));
    }
}
