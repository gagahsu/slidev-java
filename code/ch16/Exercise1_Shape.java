// Ch16 練習1：Shape 面積與周長
// 抽象類別 AbstractShape，子類別 RectShape 和 CircleShape 各自實作 area() 和 perimeter()
// 注意：Shape / Rectangle / Circle 已在 ch14/Exercise_SealedShapeRecord.java 的 default package
//       中被定義（sealed interface Shape, record Circle, record Rectangle），
//       Eclipse 視所有 chXX source folders 為同一個 default package，因此改名以避免衝突。

abstract class AbstractShape {
    public abstract double area();
    public abstract double perimeter();
}

class RectShape extends AbstractShape {
    private double height;
    private double width;

    RectShape(double height, double width) {
        this.height = height;
        this.width = width;
    }

    @Override
    public double area() {
        return height * width;
    }

    @Override
    public double perimeter() {
        return 2 * (height + width);
    }
}

class CircleShape extends AbstractShape {
    private double r;

    CircleShape(double r) {
        this.r = r;
    }

    @Override
    public double area() {
        return Math.PI * r * r;
    }

    @Override
    public double perimeter() {
        return 2 * Math.PI * r;
    }
}

public class Exercise1_Shape {
    public static void main(String[] args) {
        RectShape rect = new RectShape(2, 3);
        System.out.println("矩形面積：" + rect.area());
        System.out.println("矩形周長：" + rect.perimeter());

        CircleShape circle = new CircleShape(2);
        System.out.println("圓面積：" + circle.area());
        System.out.println("圓周長：" + circle.perimeter());
    }
}
