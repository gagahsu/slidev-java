// Ch16-adv 練習1：密封圖形系統
// 將 Shape 改寫為 sealed 抽象類別，用 permits 限制只允許 SealedRectangle、SealedCircle 繼承
// 註：類別改名為 SealedXxx，避免與 Exercise1_Shape.java 中的 Shape/Rectangle/Circle 撞名

abstract sealed class SealedShape permits SealedRectangle, SealedCircle {
    public abstract double area();
    public abstract double perimeter();
}

final class SealedRectangle extends SealedShape {
    private double height;
    private double width;

    SealedRectangle(double height, double width) {
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

final class SealedCircle extends SealedShape {
    private double r;

    SealedCircle(double r) {
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

// 證照常考：若再寫一個 class Triangle extends SealedShape，
// 因為 Triangle 不在 permits 名單中，編譯器會直接報錯

public class ExerciseAdv1_SealedShape {
    public static void main(String[] args) {
        SealedRectangle rect = new SealedRectangle(2, 3);
        System.out.println("矩形面積：" + rect.area());
        System.out.println("矩形周長：" + rect.perimeter());

        SealedCircle circle = new SealedCircle(2);
        System.out.println("圓面積：" + circle.area());
        System.out.println("圓周長：" + circle.perimeter());
    }
}
