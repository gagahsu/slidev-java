// Ch16 練習：抽象類別 vs 介面 的選擇
// 認證模擬題解析（正確答案：A）
// Dog、Cat 共享動物屬性與行為 → 用 abstract class AbstractAnimal
// makeSound() 跨越動物與機器人的「跨界能力」 → 用 interface Soundable
// 注意：Dog、Cat、Animal 已存在於 ch14 default package，此處改名避免衝突

// 介面：定義「跨界能力」CAN-DO 關係
// 介面無法儲存 name、age 這種實例狀態，所以 C 選項錯誤
interface Soundable {
    void makeSound();
}

// 抽象類別：定義「血緣相近」的共用狀態與行為 IS-A 關係
abstract class AbstractAnimal implements Soundable {
    protected String name;
    protected int age;

    AbstractAnimal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // 共用的普通方法：eat() 已有實作，子類別繼承後不需重複撰寫
    void eat() {
        System.out.println(name + " 正在吃東西");
    }
}

// Dog IS-A AbstractAnimal，共享 name、age、eat()
// Dog CAN-DO makeSound()（透過 AbstractAnimal implements Soundable 繼承過來）
class ChDog extends AbstractAnimal {
    ChDog(String name, int age) {
        super(name, age);
    }

    @Override
    public void makeSound() {
        System.out.println(name + "（狗）：汪汪！");
    }
}

// Cat IS-A AbstractAnimal，同上
class ChCat extends AbstractAnimal {
    ChCat(String name, int age) {
        super(name, age);
    }

    @Override
    public void makeSound() {
        System.out.println(name + "（貓）：喵喵！");
    }
}

// Robot 不是動物，不繼承 AbstractAnimal
// 但 Robot CAN-DO makeSound()，透過 implements Soundable 取得這個能力
class ChRobot implements Soundable {
    private String model;

    ChRobot(String model) {
        this.model = model;
    }

    @Override
    public void makeSound() {
        System.out.println(model + "（機器人）：Beep Boop！");
    }
}

public class Exercise_AbstractVsInterface {
    public static void main(String[] args) {
        // 用多形（Soundable 型態）統一呼叫 makeSound()
        Soundable[] sounds = {
            new ChDog("小黑", 3),
            new ChCat("咪咪", 2),
            new ChRobot("R2D2"),
        };

        System.out.println("=== 所有會發聲的物件 ===");
        for (Soundable s : sounds) {
            s.makeSound();
        }

        // 只有動物才能呼叫 eat()
        System.out.println("\n=== 動物的共用行為 ===");
        AbstractAnimal[] animals = {
            new ChDog("小黑", 3),
            new ChCat("咪咪", 2),
        };
        for (AbstractAnimal a : animals) {
            a.eat(); // 繼承自 AbstractAnimal，不需在每個子類別重複實作
        }

        // 結論：
        // A 正確：抽象類別負責血緣相近的共用狀態（name, age）與行為（eat()）
        //         介面負責跨血緣的共同能力（makeSound()）
        // B 錯誤：Robot 不是動物，不應 extends AbstractAnimal
        // C 錯誤：interface 無法儲存 name, age 這種實例狀態
        // D 錯誤：各自定義無法用多形統一處理，也無法強制實作 makeSound()
        System.out.println("\n正確答案：A（抽象類別 + 介面混合使用）");
    }
}
