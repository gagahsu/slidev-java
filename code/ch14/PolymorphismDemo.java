/**
 * 練習 2：多形練習
 * 建立 Animal、Dog、Bird，各自 Override move()
 * 用 Animal[] 陣列搭配迴圈呼叫 move()，觀察多形效果
 */
public class PolymorphismDemo {

    static class Animal {
        protected String name;

        Animal(String name) {
            this.name = name;
        }

        void move() {
            System.out.println(name + " is moving.");
        }
    }

    static class Dog extends Animal {
        Dog(String name) {
            super(name);
        }

        @Override
        void move() {
            System.out.println(name + " is running on four legs! 🐕");
        }
    }

    static class Bird extends Animal {
        Bird(String name) {
            super(name);
        }

        @Override
        void move() {
            System.out.println(name + " is flying with wings! 🐦");
        }
    }

    public static void main(String[] args) {
        // 用父類別型態宣告陣列，存放各種子類別物件
        Animal[] animals = { new Dog("旺財"), new Bird("小翠"), new Dog("黑皮"), new Bird("白鷺") };

        System.out.println("=== 多形陣列示範 ===");
        for (Animal a : animals) {
            a.move(); // 動態綁定（dynamic binding）：執行時決定呼叫哪個 move()
        }

        System.out.println("\n=== instanceof 型別判斷 ===");
        for (Animal a : animals) {
            if (a instanceof Dog) {
                System.out.println(a.name + " 是狗");
            } else if (a instanceof Bird) {
                System.out.println(a.name + " 是鳥");
            }
        }
    }
}
