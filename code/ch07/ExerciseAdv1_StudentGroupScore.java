// Ch07-adv Part 1 練習：學生分組成績表
// 練習不規則陣列（每組人數不同）並計算每組平均分數
// 業界常用：每個項目資料量不同時，不規則陣列避免浪費空間補齊最大長度

public class ExerciseAdv1_StudentGroupScore {
    public static void main(String[] args) {
        // 不規則陣列：3 組，各組人數分別為 2、4、3 人
        int[][] groupScores = new int[3][];
        groupScores[0] = new int[]{85, 90};          // 第 1 組：2 人
        groupScores[1] = new int[]{70, 80, 75, 78};  // 第 2 組：4 人
        groupScores[2] = new int[]{92, 88, 90};       // 第 3 組：3 人

        for (int i = 0; i < groupScores.length; i++) {
            int sum = 0;
            // 內層上限用 groupScores[i].length，每組長度不同，不能寫死
            for (int j = 0; j < groupScores[i].length; j++) {
                sum += groupScores[i][j];
            }
            // 證照常考：整數除以整數會截去小數，記得轉 double
            double avg = (double) sum / groupScores[i].length;
            System.out.printf("第 %d 組平均：%.2f%n", i + 1, avg);
        }

        // 思考：設為 null 後，原本的陣列資料不會立刻消失，
        // 只是失去參照成為 GC 候選，JVM 觸發 GC 時才釋放記憶體
        // groupScores = null;
    }
}
