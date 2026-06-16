// Ch16-adv 綜合練習：密封式遊戲框架
// 結合 Template Method 與 Sealed：abstract sealed class Game permits Chess, Soccer

abstract sealed class Game permits Chess, Soccer {
    abstract void start();
    abstract void end();

    // final 鎖住流程順序：先 start 再 end
    final void play() {
        start();
        end();
    }
}

final class Chess extends Game {
    @Override
    void start() {
        System.out.println("走棋");
    }

    @Override
    void end() {
        System.out.println("將軍");
    }
}

final class Soccer extends Game {
    @Override
    void start() {
        System.out.println("踢球");
    }

    @Override
    void end() {
        System.out.println("進球");
    }
}

// 證照常考：若再寫一個 class Poker extends Game，
// 因為 Poker 不在 permits 名單中，編譯器會直接報錯

public class ExerciseAdv_SealedGameFramework {
    public static void main(String[] args) {
        new Chess().play();  // 走棋 → 將軍
        new Soccer().play(); // 踢球 → 進球
    }
}
