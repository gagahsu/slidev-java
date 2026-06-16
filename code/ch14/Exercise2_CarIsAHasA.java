// Ch14 練習：IS-A 與 HAS-A 綜合判斷
// Car IS-A Vehicle（繼承），Car HAS-A Engine（聚合）

class Engine {
    public void start() {
        System.out.println("引擎發動");
    }
}

class Vehicle {
    public void run() {
        System.out.println("車輛行駛中");
    }
}

class Car extends Vehicle {
    private Engine engine = new Engine(); // HAS-A：聚合關係

    public void drive() {
        engine.start();
        System.out.println("汽車出發");
    }
}

public class Exercise2_CarIsAHasA {
    public static void main(String[] args) {
        Car car = new Car();
        car.run();   // 繼承自 Vehicle（IS-A）
        car.drive(); // 委派給 Engine（HAS-A）

        System.out.println(car instanceof Vehicle); // true，驗證 IS-A 關係
    }
}
