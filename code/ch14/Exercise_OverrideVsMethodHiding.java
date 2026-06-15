// Ch14 練習：Override 與方法隱藏辨析（認證模擬題）
// static 方法的「方法隱藏」是靜態綁定（看變數宣告型態）；
// 一般方法的 Override 是動態綁定（看物件實際型態）— OCA/OCP 經典陷阱題

class AnimalSound {
    static void sound() {
        System.out.println("Animal sound");
    }

    void move() {
        System.out.println("Animal move");
    }
}

class DogSound extends AnimalSound {
    // static 方法不能被 Override，只能「隱藏（hide）」父類別的同名 static 方法
    static void sound() {
        System.out.println("Dog sound");
    }

    @Override
    void move() {
        System.out.println("Dog move");
    }
}

public class Exercise_OverrideVsMethodHiding {
    public static void main(String[] args) {
        AnimalSound a = new DogSound();

        // a.sound() 是 static 方法呼叫，屬於「方法隱藏」，
        // 由變數的「宣告型態」AnimalSound 決定 → 印出 "Animal sound"
        a.sound();

        // a.move() 是一般實例方法，DogSound 有 @Override，屬於「動態綁定」，
        // 由物件的「實際型態」DogSound 決定 → 印出 "Dog move"
        a.move();

        // 認證模擬題答案：C（Animal sound 與 Dog move）
        // 記住口訣：static 方法看宣告型態，一般方法看實際型態
    }
}
