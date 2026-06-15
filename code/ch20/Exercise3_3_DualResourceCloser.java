// Ch20 練習3-3：雙資源複製與自訂關閉器
// try-with-resources 同時管理 Scanner 與自訂 AutoCloseable（Logger）
// 觀察兩個資源的關閉順序：與宣告順序相反，先關 Logger，再關 Scanner

import java.util.Scanner;

// 只要實作 AutoCloseable 介面（實作 close() 方法），自訂類別就能放進 try(...)
class Logger implements AutoCloseable {
    Logger() {
        System.out.println("Logger 啟動");
    }

    void log(String msg) {
        System.out.println("[LOG] " + msg);
    }

    @Override
    public void close() {
        System.out.println("Logger 關閉");
    }
}

public class Exercise3_3_DualResourceCloser {
    public static void main(String[] args) {
        // 在 try(...) 中同時宣告 Scanner 與 Logger，用分號分隔
        // 關閉順序與宣告順序相反：先關 logger，再關 sc
        try (Scanner sc = new Scanner(System.in);
             Logger logger = new Logger()) {

            System.out.print("請輸入一個整數：");
            int input = sc.nextInt();
            logger.log("使用者輸入：" + input);

        } catch (Exception e) {
            System.out.println("輸入格式錯誤：" + e.getMessage());
        }
        // 離開 try 區塊後，自動依序印出「Logger 關閉」→「（Scanner 無輸出，但已被關閉）」
    }
}
