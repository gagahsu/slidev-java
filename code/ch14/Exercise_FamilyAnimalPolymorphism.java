// Ch14 練習：繼承與多形綜合練習
// 第一部分：Father / Son / Daughter 繼承練習（protected 欄位 + super 呼叫 + Override）
// 第二部分：Animal / Dog / Bird 多形陣列練習

// ---- 第一部分：繼承練習 ----
class Father {
    protected String name;

    public Father(String name) {
        this.name = name;
    }

    public void walk() {
        System.out.println(name + " is walking!!!");
    }
}

class Son extends Father {
    public Son(String name) {
        super(name); // 用 super(name) 初始化父類別欄位
    }

    @Override
    public void walk() {
        System.out.println(name + " is walking~~~");
    }

    public void playBall() {
        System.out.println(name + " is playing ball");
    }
}

class Daughter extends Father {
    public Daughter(String name) {
        super(name);
    }

    @Override
    public void walk() {
        System.out.println(name + " is walking@@@");
    }

    public void shopping() {
        System.out.println(name + " is shopping");
    }
}

// ---- 第二部分：多形練習 ----
class FamilyAnimal {
    protected String petName;

    public FamilyAnimal(String petName) {
        this.petName = petName;
    }

    public void move() {
        System.out.println(petName + " Animal 移動");
    }
}

class FamilyDog extends FamilyAnimal {
    public FamilyDog(String petName) {
        super(petName);
    }

    @Override
    public void move() {
        System.out.println(petName + " 跑步");
    }
}

class FamilyBird extends FamilyAnimal {
    public FamilyBird(String petName) {
        super(petName);
    }

    @Override
    public void move() {
        System.out.println(petName + " 飛翔");
    }
}

public class Exercise_FamilyAnimalPolymorphism {
    public static void main(String[] args) {
        // 第一部分：繼承練習
        Father father = new Father("爸爸");
        Son son = new Son("兒子");
        Daughter daughter = new Daughter("女兒");

        father.walk();      // 爸爸 is walking!!!
        son.walk();          // 兒子 is walking~~~
        son.playBall();      // 兒子 is playing ball
        daughter.walk();     // 女兒 is walking@@@
        daughter.shopping(); // 女兒 is shopping

        System.out.println();

        // 第二部分：多形練習
        // 父類別型態陣列存放不同子類別物件（Upcasting）
        FamilyAnimal[] animals = {
                new FamilyDog("旺財"),
                new FamilyBird("小翠")
        };

        for (FamilyAnimal animal : animals) {
            animal.move(); // 動態綁定：依物件實際型態呼叫對應的 move()
        }
        // 旺財 跑步
        // 小翠 飛翔
    }
}
