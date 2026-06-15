// Ch18 綜合練習：簡易屬性面板
// 用 Scanner 讀取角色名稱與生命值字串，搭配 isInteger() 驗證格式，
// 並用 Integer.min() 限制生命值上限，串接本章學過的包裝類別工具

import java.util.Scanner;

public class Exercise_AttributePanel {

    // 沿用「判斷字串是否為合法整數」的寫法：try-catch 包住 parseInt
    static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("請輸入角色名稱：");
        String charName = sc.nextLine();

        System.out.print("請輸入生命值：");
        String hpStr = sc.nextLine();
        sc.close();

        // 若生命值字串不合法（例如 "abc"），視為傷害 0 → 生命值設為 0
        int hp = isInteger(hpStr) ? Integer.parseInt(hpStr) : 0;

        // 限制生命值不超過上限 9999：用 Integer.min 取兩者較小值
        // 易錯點：要用 min（設上限），不是 max（設下限）
        int finalHp = Integer.min(hp, 9999);

        System.out.println("角色：" + charName + "，生命值：" + finalHp);
        // 範例輸入：炭治郎 / abc → 角色：炭治郎，生命值：0
    }
}
