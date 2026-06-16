// Ch17-adv 練習：解決鑽石問題
// Worker 為源頭，IManager 與 IEngineer 各自 override work()，TeamLead 同時實作兩者形成鑽石結構
// 注意：Manager / Engineer 已在 ch14/Exercise_EmployeeManager.java 及
//       ch16/Exercise_EmployeeAbstract.java 的 default package 中被定義（class），
//       這裡改名為 IManager / IEngineer 以避免 Eclipse 跨 source folder 的 default package 衝突。

interface Worker {
    default void work() {
        System.out.println("工作中...");
    }
}

interface IManager extends Worker {
    @Override
    default void work() {
        System.out.println("主管：分配任務");
    }
}

interface IEngineer extends Worker {
    @Override
    default void work() {
        System.out.println("工程師：寫程式");
    }
}

class TeamLead implements IManager, IEngineer {
    @Override
    public void work() {
        // 證照常考：TeamLead 透過 IManager.super / IEngineer.super 拿到的是
        // IManager、IEngineer 各自 override 後的版本，不會印出 Worker 的「工作中...」
        IManager.super.work();
        IEngineer.super.work();
    }
}

public class ExerciseAdv_TeamLeadDiamond {
    public static void main(String[] args) {
        TeamLead lead = new TeamLead();
        lead.work();
        // 主管：分配任務
        // 工程師：寫程式
    }
}
