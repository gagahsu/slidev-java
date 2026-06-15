// Ch14 自學練習三：巢狀類別綜合應用
// 一般內部類別（Inner Class）依附外部類別物件存在，
// 匿名內部類別（Anonymous Class）宣告同時建立物件並 override 方法

class Zoo {
    String zooName = "動物王國";

    // 一般內部類別：可直接存取外部類別的屬性
    class Cage {
        void show() {
            System.out.println(zooName + " 的籠子");
        }
    }
}

// 給匿名內部類別練習用的簡單父類別
class Creature {
    void move() {
        System.out.println("Creature move");
    }
}

public class Exercise_NestedZooAnonymous {
    public static void main(String[] args) {
        // 一般內部類別：必須先有外部類別物件，才能 new 出內部類別物件
        Zoo zoo = new Zoo();
        Zoo.Cage cage = zoo.new Cage();
        cage.show(); // 動物王國 的籠子

        // 匿名內部類別：宣告同時完成 extends 並 override move()
        Creature c = new Creature() {
            @Override
            void move() {
                System.out.println("匿名動物移動中");
            }
        };
        c.move();
    }
}
