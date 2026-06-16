// Ch16 練習：Employee 抽象類別混用兩種方法
// 普通方法 clockIn() 所有子類別共用，抽象方法 work() 由各子類別自行實作
// 注意：Employee 已在 ch14/Exercise_EmployeeManager.java 的 default package 中被定義，
//       這裡改名為 AbstractEmployee 以避免 Eclipse 跨 source folder 的 default package 衝突。

abstract class AbstractEmployee {
    void clockIn() { // 普通方法，共用邏輯，不需 override
        System.out.println("打卡上班");
    }

    abstract void work(); // 抽象方法，各自實作
}

class Engineer extends AbstractEmployee {
    @Override
    void work() {
        System.out.println("撰寫程式");
    }
}

class Designer extends AbstractEmployee {
    @Override
    void work() {
        System.out.println("設計畫面");
    }
}

public class Exercise_EmployeeAbstract {
    public static void main(String[] args) {
        AbstractEmployee e1 = new Engineer();
        AbstractEmployee e2 = new Designer();

        e1.clockIn();
        e1.work(); // 打卡上班 → 撰寫程式

        e2.clockIn();
        e2.work(); // 打卡上班 → 設計畫面
    }
}
