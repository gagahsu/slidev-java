// Ch07-adv Part 2 練習：單位轉換工具
// 命令列工具：接收「數字」與「單位代號（km 或 mi）」，將公里換算英里或反向換算
// 業界常用：命令列工具透過 args 接收參數，先檢查 length 再存取，避免 ArrayIndexOutOfBoundsException

public class ExerciseAdv2_UnitConverter {
    // 換算公式：1 km = 0.6214 mi
    static final double KM_TO_MI = 0.6214;

    public static void main(String[] args) {
        // 先檢查參數數量，不足則印出使用說明
        if (args.length < 2) {
            System.out.println("用法：java ExerciseAdv2_UnitConverter <數字> <km|mi>");
            return;
        }

        // 命令列傳入的全是字串，需要轉型
        // 證照常考：若 args[0] 不是合法數字，Double.parseDouble 會拋出 NumberFormatException
        double value = Double.parseDouble(args[0]);
        String unit = args[1];

        if (unit.equals("km")) {
            double miles = value * KM_TO_MI;
            System.out.printf("%.1f km = %.3f mi%n", value, miles);
        } else if (unit.equals("mi")) {
            double km = value / KM_TO_MI;
            System.out.printf("%.1f mi = %.3f km%n", value, km);
        } else {
            System.out.println("不支援的單位：" + unit + "（請輸入 km 或 mi）");
        }
    }
}
