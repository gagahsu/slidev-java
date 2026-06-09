// Ch16 練習2：抽象數學計算器
// 抽象類別 MyMath 含普通方法 output() 和抽象方法 add()、mul()
// 子類別 MyTest 實作這兩個抽象方法

abstract class MyMath {
    // 抽象方法：由子類別實作
    abstract int add(int n1, int n2);
    abstract int mul(int n1, int n2);

    // 普通方法：直接繼承，不需 override
    void output() {
        System.out.println("我的計算器");
    }
}

class MyTest extends MyMath {
    @Override
    int add(int n1, int n2) {
        return n1 + n2;
    }

    @Override
    int mul(int n1, int n2) {
        return n1 * n2;
    }
}

public class Exercise2_MyMath {
    public static void main(String[] args) {
        // Upcasting：用父類別型態承接子類別物件
        MyMath obj = new MyTest();
        obj.output();
        System.out.println("加法結果：" + obj.add(3, 8));
        System.out.println("乘法結果：" + obj.mul(3, 8));
    }
}
