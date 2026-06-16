// Ch19 自學 綜合練習：設計模組宣告檔（module-info.java）
//
// 本練習主題是 Java 平台模組系統（JPMS, Java 9+）的 module-info.java 語法。
// 由於本專案 code/ 是傳統 classpath（flat classpath）專案，並非 JPMS 模組專案，
// 為避免破壞 Eclipse 的編譯設定，這裡不建立真正的 module-info.java，
// 而是用一般的 .java 檔以 Javadoc/字串常數的方式，示範解題提示的內容。

package moduleinfo;

public class ExerciseAdv_ModuleInfoDesign {

    /**
     * 任務說明：
     * 延續基礎版「學生管理系統」的套件結構：
     *   com.school.model     <- Student 類別
     *   com.school.service   <- StudentService 類別（使用 Student）
     *   com.school           <- Main（使用 Student、StudentService）
     *
     * 請將這三個套件包成「一個模組」 school.app，撰寫對應的 module-info.java：
     *   1. 模組名稱為 school.app
     *   2. exports com.school.model，讓其他模組也能使用 Student
     *   3. exports com.school.service，但只開放給 school.ui 模組使用
     */

    // 解題提示：module-info.java 應放在模組原始碼的根目錄（src/module-info.java）
    // 內容如下（以字串常數呈現，避免在 flat classpath 專案中放入真正的 module-info.java）：
    static final String MODULE_INFO_SOURCE =
            "// src/module-info.java\n" +
            "module school.app {\n" +
            "    exports com.school.model;\n" +
            "    exports com.school.service to school.ui;\n" +
            "}\n";

    public static void main(String[] args) {
        System.out.println("=== module-info.java 解題提示 ===");
        System.out.println(MODULE_INFO_SOURCE);

        System.out.println("--- 重點說明 ---");
        System.out.println("module       : 宣告模組名稱，例如 module school.app { ... }");
        System.out.println("requires     : 宣告本模組依賴哪些其他模組（例如 requires java.sql;）");
        System.out.println("exports      : 宣告哪些套件要對外開放");
        System.out.println("exports ... to ... : 只對指定模組開放，限制存取對象");
        System.out.println();
        System.out.println("決策依據：");
        System.out.println("- com.school.model   是資料模型(DTO)，可能被多個模組共用 -> 對所有模組 exports");
        System.out.println("- com.school.service 是商業邏輯，只給特定 UI 模組使用 -> exports ... to school.ui");
        System.out.println("- com.school（含 Main）是程式進入點，不需要被其他模組引用 -> 不需要 exports");
    }
}
