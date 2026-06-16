// Ch19 練習 3：解決命名衝突
// java.util.Date 與 java.sql.Date 同名，示範如何用 import + 全限定名稱避免衝突
//
// 做法（模仿 ch17 Exercise_InterfaceHierarchy 處理 Runnable 撞名的方式）：
// 1. 只 import 較常用的 java.util.Date，程式碼中用短名稱 Date
// 2. 較少用的 java.sql.Date 不 import，使用時寫出全限定名稱 java.sql.Date

package naming;

import java.util.Date;

public class Exercise3_DateNamingConflict {
    public static void main(String[] args) {
        // 1. java.util.Date：代表「目前時間」（已 import，使用短名稱）
        Date utilDate = new Date();
        System.out.println("java.util.Date（目前時間）：" + utilDate);

        // 2. java.sql.Date：代表「2024-01-01」（未 import，使用全限定名稱）
        java.sql.Date sqlDate = new java.sql.Date(
                java.time.LocalDate.of(2024, 1, 1)
                        .atStartOfDay(java.time.ZoneId.systemDefault())
                        .toInstant().toEpochMilli());
        System.out.println("java.sql.Date（2024-01-01）：" + sqlDate);

        // 說明：兩個同名類別若都 import 會造成編譯錯誤（衝突）。
        // 正確做法：較常用的那個 import 取短名稱，另一個在使用處寫全名。
    }
}
