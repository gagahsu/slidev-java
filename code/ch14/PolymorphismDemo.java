/**
 * 練習 2：多形練習
 * 建立 Animal、Dog、Bird，各自 Override move()
 * 用 Animal[] 陣列搭配迴圈呼叫 move()，觀察多形效果
 */
class Animal {
    protected String name;

    Animal(String name) {
        this.name = name;
    }

    void move() {
        System.out.println(name + " 移動");
    }
}

class Dog extends Animal {
    Dog(String name) {
        super(name);
    }

    @Override
    void move() {
        System.out.println(name + " 跑步");
    }
}

class Bird extends Animal {
    Bird(String name) {
        super(name);
    }

    @Override
    void move() {
        System.out.println(name + " 飛翔");
    }
}

public class PolymorphismDemo {
    public static void main(String[] args) {
        // 用父類別型態宣告陣列，存放各種子類別物件
        Animal[] animals = { new Dog("旺財"), new Bird("小翠") };

        for (int i = 0; i < animals.length; i++) {
            animals[i].move(); // 動態綁定：執行時依實際型態決定呼叫哪個 move()
        }
    }
}
