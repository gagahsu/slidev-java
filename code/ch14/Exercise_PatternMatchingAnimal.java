// Ch14 自學練習二：Pattern Matching 與綁定判斷
// 用 Pattern Matching for instanceof 判斷型態並直接取得轉型後的變數，
// 並驗證 static 方法的「方法隱藏」是靜態綁定（看宣告型態）

class Animal {
    static void info() {
        System.out.println("Animal info");
    }

    void move() {
        System.out.println("Animal move");
    }
}

class Dog extends Animal {
    static void info() {
        System.out.println("Dog info");
    }

    @Override
    void move() {
        System.out.println("Dog move");
    }

    void barking() {
        System.out.println("汪汪！");
    }
}

class Cat extends Animal {
    @Override
    void move() {
        System.out.println("Cat move");
    }

    void meow() {
        System.out.println("喵～");
    }
}

public class Exercise_PatternMatchingAnimal {

    static void inspect(Object obj) {
        // Pattern Matching for instanceof：判斷型態的同時直接取得轉型後的變數
        if (obj instanceof Dog d) {
            System.out.println("這是一隻狗");
            d.barking();
        } else if (obj instanceof Cat c) {
            System.out.println("這是一隻貓");
            c.meow();
        }
    }

    public static void main(String[] args) {
        inspect(new Dog());
        inspect(new Cat());

        // 第 3 小題：static 方法是方法隱藏（method hiding），屬於靜態綁定
        // 由變數的「宣告型態」Animal 決定，呼叫的是 Animal.info()，不是 Dog.info()
        Animal a = new Dog();
        a.move(); // 動態綁定 → Dog move
        a.info(); // 靜態綁定 → Animal info（證照常考：static 方法看宣告型態）
    }
}
