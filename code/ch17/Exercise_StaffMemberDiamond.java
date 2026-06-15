// Ch17-adv 練習：解決多重介面的 Default 衝突
// StaffMember 同時實作 Trainer 與 Receptionist，兩者皆有 greet()，需用 介面.super 分別呼叫

interface Trainer {
    default void greet() {
        System.out.println("教練：開始上課！");
    }
}

interface Receptionist {
    default void greet() {
        System.out.println("櫃台：歡迎光臨！");
    }
}

class StaffMember implements Trainer, Receptionist {
    @Override
    public void greet() {
        // 證照常考：同時實作兩個有同名 default 方法的介面，必須強制 override
        // 用「介面名稱.super.方法名稱()」分別呼叫各自的版本
        Trainer.super.greet();
        Receptionist.super.greet();
    }
}

public class Exercise_StaffMemberDiamond {
    public static void main(String[] args) {
        StaffMember staff = new StaffMember();
        staff.greet();
        // 教練：開始上課！
        // 櫃台：歡迎光臨！
    }
}
