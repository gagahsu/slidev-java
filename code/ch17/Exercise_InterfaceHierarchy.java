// Ch17 練習：設計介面階層
// 自訂 MyRunnable 介面（不直接命名為 Runnable，避免與多執行緒用的 java.lang.Runnable 撞名）

interface MyRunnable {
    void run();
}

class Human implements MyRunnable {
    @Override
    public void run() {
        System.out.println("人在路上跑");
    }
}

class Car implements MyRunnable {
    @Override
    public void run() {
        System.out.println("車在公路上跑");
    }
}

// Person 繼承 Human，重新定義 run()
class Person extends Human {
    @Override
    public void run() {
        System.out.println("人在操場上跑");
    }
}

public class Exercise_InterfaceHierarchy {
    public static void main(String[] args) {
        Human human = new Human();
        human.run();  // 人在路上跑

        Person person = new Person();
        person.run(); // 人在操場上跑

        Car car = new Car();
        car.run();    // 車在公路上跑
    }
}
