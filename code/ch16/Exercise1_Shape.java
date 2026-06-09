// Ch16 練習1：Shape 面積與周長
// 抽象類別 Shape，子類別 Rectangle 和 Circle 各自實作 area() 和 perimeter()

abstract class Shape {
    public abstract double area();
    public abstract double perimeter();
}

class Rectangle extends Shape {
    private double height;
    private double width;

    Rectangle(double height, double width) {
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

class Circle extends Shape {
    private double r;

    Circle(double r) {
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
        Rectangle rect = new Rectangle(2, 3);
        System.out.println("矩形面積：" + rect.area());
        System.out.println("矩形周長：" + rect.perimeter());

        Circle circle = new Circle(2);
        System.out.println("圓面積：" + circle.area());
        System.out.println("圓周長：" + circle.perimeter());
    }
}
