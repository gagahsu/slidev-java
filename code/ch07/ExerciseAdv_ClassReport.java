// Ch07-adv 綜合練習：成績統計小工具
// 練習不規則陣列（每班人數不同）搭配命令列參數 args 查詢指定班級的總分與平均

import java.util.Arrays;

public class ExerciseAdv_ClassReport {
    public static void main(String[] args) {
        // 不規則陣列：每個元素是長度不同的 int[]
        int[][] classScores = new int[3][];
        classScores[0] = new int[]{80, 90, 75};
        classScores[1] = new int[]{60, 70, 85, 95, 88};
        classScores[2] = new int[]{100, 92};

        // 業界慣例：若沒有提供命令列參數，預設查詢第 1 班，避免直接拋例外
        int classNo = (args.length > 0) ? Integer.parseInt(args[0]) : 1;

        // 證照常考：題目從 1 開始編號，但陣列索引從 0 開始，記得 -1
        int[] target = classScores[classNo - 1];

        System.out.println("第 " + classNo + " 班成績：" + Arrays.toString(target));

        int sum = 0;
        for (int score : target) {
            sum += score;
        }
        double avg = (double) sum / target.length;

        System.out.printf("總分：%d，平均：%.2f%n", sum, avg);
    }
}
