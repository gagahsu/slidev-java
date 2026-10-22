/**
 * 練習 1：繼承練習
 * 父類別 Father，子類別 Son 和 Daughter
 *   Father：name(String)、walk() 印出 "name is walking!!!"
 *   Son：Override walk() 印 "name is walking~~~"，加上 playBall()
 *   Daughter：Override walk() 印 "name is walking@@@"，加上 shopping()
 */
class Father {
    protected String name;

    Father(String name) {
        this.name = name;
    }

    void walk() {
        System.out.println(name + " is walking!!!");
    }
}

class Son extends Father {
    Son(String name) {
        super(name);
    }

    @Override
    void walk() {
        System.out.println(name + " is walking~~~");
    }

    void playBall() {
        System.out.println(name + " is playing ball!");
    }
}

class Daughter extends Father {
    Daughter(String name) {
        super(name);
    }

    @Override
    void walk() {
        System.out.println(name + " is walking@@@");
    }

    void shopping() {
        System.out.println(name + " is shopping!");
    }
}

public class InheritanceDemo {
    public static void main(String[] args) {
        Father father = new Father("Dad");
        Son son = new Son("Tom");
        Daughter daughter = new Daughter("Amy");

        father.walk();
        son.walk();
        son.playBall();
        daughter.walk();
        daughter.shopping();
    }
}
