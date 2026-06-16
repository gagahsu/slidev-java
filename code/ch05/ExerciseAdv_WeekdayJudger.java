// Ch05 進階練習一：星期幾判斷器
// 用 Switch Expression（Java 14+）搭配多值 case，判斷工作日或假日

public class ExerciseAdv_WeekdayJudger {
    public static void main(String[] args) {
        int day = 6; // 1=星期一 ... 7=星期日

        // Switch Expression：箭頭語法不需要 break，每個 case 獨立
        String type = switch (day) {
            case 1, 2, 3, 4, 5 -> "工作日";
            case 6, 7           -> "假日";
            default             -> "無效輸入";
        };

        System.out.println(type); // 預期輸出：假日
    }
}
