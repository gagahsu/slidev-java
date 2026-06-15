// Ch14 綜合練習：密封圖形系統
// 結合 Sealed Classes、Records、Pattern Matching 三個主題：
// 用 instanceof Pattern Matching 窮舉判斷實際型態
// 註：switch 的 Pattern Matching 在 JDK 17 仍是預覽功能，要到 JDK 21 才正式定案，
//     這裡改寫成 if/else + instanceof，JDK 17 可直接編譯

sealed interface Shape permits Circle, Rectangle { }

record Circle(double radius) implements Shape {
    double area() {
        return radius * radius * Math.PI;
    }
}

record Rectangle(double w, double h) implements Shape {
    double area() {
        return w * h;
    }
}

public class Exercise_SealedShapeRecord {

    static void printArea(Object obj) {
        if (obj instanceof Circle c) {
            System.out.println("Circle 面積：" + c.area());
        } else if (obj instanceof Rectangle r) {
            System.out.println("Rectangle 面積：" + r.area());
        } else {
            System.out.println("不是 Shape，略過");
        }
    }

    public static void main(String[] args) {
        printArea(new Circle(2));
        printArea(new Rectangle(3, 4));
        printArea("不是圖形");
    }
}
