// Ch17-adv 練習：解決鑽石問題
// Worker 為源頭，Manager 與 Engineer 各自 override work()，TeamLead 同時實作兩者形成鑽石結構

interface Worker {
    default void work() {
        System.out.println("工作中...");
    }
}

interface Manager extends Worker {
    @Override
    default void work() {
        System.out.println("主管：分配任務");
    }
}

interface Engineer extends Worker {
    @Override
    default void work() {
        System.out.println("工程師：寫程式");
    }
}

class TeamLead implements Manager, Engineer {
    @Override
    public void work() {
        // 證照常考：TeamLead 透過 Manager.super / Engineer.super 拿到的是
        // Manager、Engineer 各自 override 後的版本，不會印出 Worker 的「工作中...」
        Manager.super.work();
        Engineer.super.work();
    }
}

public class Exercise_TeamLeadDiamond {
    public static void main(String[] args) {
        TeamLead lead = new TeamLead();
        lead.work();
        // 主管：分配任務
        // 工程師：寫程式
    }
}
