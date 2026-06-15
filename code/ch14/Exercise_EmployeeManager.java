// Ch14 練習：設計 Employee 繼承體系
// 練習 extends、protected 屬性與 super()：Manager 繼承 Employee，
// 並 override showSalary() 計算底薪 + 獎金

class Employee {
    protected String name;
    protected int baseSalary;

    public Employee(String name, int baseSalary) {
        this.name = name;
        this.baseSalary = baseSalary;
        System.out.println("Employee 建構");
    }

    public void showSalary() {
        System.out.println(name + " 的底薪是 " + baseSalary);
    }
}

class Manager extends Employee {
    private int bonus;

    public Manager(String name, int baseSalary, int bonus) {
        super(name, baseSalary); // 業界慣例：子類別建構方法第一行呼叫 super()，先完成父類別初始化
        this.bonus = bonus;
        System.out.println("Manager 建構");
    }

    @Override
    public void showSalary() {
        System.out.println(name + " 的總薪資是 " + (baseSalary + bonus));
    }
}

public class Exercise_EmployeeManager {
    public static void main(String[] args) {
        // 觀察建構方法的執行順序：父類別先建構，子類別後建構
        Manager manager = new Manager("古古", 50000, 30000);
        manager.showSalary();
    }
}
