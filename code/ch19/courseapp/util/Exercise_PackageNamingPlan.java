// Ch19 練習 1：規劃套件命名
// 任務：公司網域 myschool.edu.tw，開發「線上選課系統」，請規劃套件結構

package courseapp.util;

/**
 * 套件命名規劃（解題提示）：
 *
 * 1. 網域 myschool.edu.tw 反轉後 -> tw.edu.myschool
 * 2. 加上專案名稱（第二層） -> tw.edu.myschool.courseapp
 * 3. 依功能模組分出第三層：
 *    - tw.edu.myschool.courseapp.model    資料模型（Student、Course）
 *    - tw.edu.myschool.courseapp.service  選課邏輯（EnrollmentService）
 *    - tw.edu.myschool.courseapp.util     工具類別（ReportPrinter）
 *
 * 本範例專案因為要放在同一個 code/ch19/ 目錄下避免與其他練習衝突，
 * 改用較短的 courseapp 為頂層套件名稱，但分層概念與上面規劃一致：
 *    - courseapp.model   -> 練習 2 的 Course 類別
 *    - courseapp.util    -> 本檔案，模擬「工具類別」這一層
 */
public class Exercise_PackageNamingPlan {

    // 模擬「報表印出」工具類別會做的事
    static class ReportPrinter {
        static void printHeader(String title) {
            System.out.println("=== " + title + " ===");
        }
    }

    public static void main(String[] args) {
        ReportPrinter.printHeader("線上選課系統 - 套件命名規劃");
        System.out.println("model   套件：tw.edu.myschool.courseapp.model");
        System.out.println("service 套件：tw.edu.myschool.courseapp.service");
        System.out.println("util    套件：tw.edu.myschool.courseapp.util");
    }
}
