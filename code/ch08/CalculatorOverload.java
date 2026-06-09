class Calculator {
    int add(int a, int b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }

    double add(double a, double b) {
        return a + b;
    }
}

public class CalculatorOverload {
    public static void main(String[] args) {
        Calculator calc = new Calculator();

        System.out.println(calc.add(1, 2));       // 3
        System.out.println(calc.add(1, 2, 3));    // 6
        System.out.println(calc.add(1.5, 2.5));   // 4.0
    }
}
