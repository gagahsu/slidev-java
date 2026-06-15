// Ch02 練習：抓出結構與命名問題
// 修正原始程式碼中的結構順序與命名慣例問題

// 原始程式碼（有問題，僅供對照，不可編譯執行）：
//
// public class main {
//     public static void main(String[] Args) {
//         int Total_Score = 90;
//         System.out.println(Total_Score);
//     }
// }
// import java.util.Scanner;
//
// 問題：
// 1. import 放在 class 後面 -> 應放在檔案最上方（package 之後、class 之前）
// 2. 類別名稱 main 全小寫 -> 應為 Pascal Case，例如 Main
// 3. 參數名稱 Args 大寫開頭 -> 應為 camelCase，例如 args
// 4. 變數名稱 Total_Score -> 應為 camelCase，例如 totalScore

import java.util.Scanner; // 業界慣例：import 一律放在檔案最上方

public class Exercise_StructureNaming {
    public static void main(String[] args) {
        int totalScore = 90;
        System.out.println(totalScore);
    }
}
