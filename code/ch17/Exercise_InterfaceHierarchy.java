// Ch17 練習：設計介面階層
// 自訂 MyRunnable 介面（提醒：若直接命名為 Runnable，會與 java.lang.Runnable 撞名
// 投影片提示：「如果你在實作時發現有個東西叫 java.lang.Runnable，
// 那是 Java 內建用來跑 Thread 的。名字取太好，也是會撞衫的。」）

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
