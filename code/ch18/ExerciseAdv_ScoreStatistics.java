// Ch18 練習：成績統計小工具
// 寫一個方法 printScore(Number score)，同時接受 Integer 和 Double 兩種型別，
// 示範 Number 抽象父類別「一個方法接所有數值型別」的彈性

public class ExerciseAdv_ScoreStatistics {

    // 參數型別宣告為 Number，Integer、Double、Float、Long 都可以傳入
    static void printScore(Number score) {
        System.out.println("成績：" + score + "　取整數後：" + score.intValue());
        // 注意：intValue() 是「直接砍掉小數點」，不是四捨五入
    }

    public static void main(String[] args) {
        printScore(Integer.valueOf(88));   // 成績：88　取整數後：88
        printScore(Double.valueOf(92.5));  // 成績：92.5　取整數後：92

        // 業界實務：如果不用 Number，就要分別寫
        // printScore(Integer) 跟 printScore(Double) 兩個多載方法，
        // 邏輯幾乎一樣卻要重複寫兩份 — 用 Number 可以避免這種重複
    }
}
