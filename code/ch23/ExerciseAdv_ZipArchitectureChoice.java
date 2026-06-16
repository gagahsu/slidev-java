// Ch23 練習：ZIP 套件架構與選用
// 認證模擬題：ZipInputStream vs ZipFile 的選用原則，答案選 B

public class ExerciseAdv_ZipArchitectureChoice {

    public static void main(String[] args) {
        // 這題是觀念題，請回答下列何者正確：
        //
        // 情境：從網路上即時收到「不確定長度、邊下載邊到達」的 ZIP 資料串流，
        //       並在收到每個檔案時立刻處理內容。
        //
        // A. 應選 ZipFile，因支援隨機存取效能好
        // B. 應選 ZipInputStream，可處理任何 InputStream（包括網路串流），採循序讀取  ← 正確答案
        // C. ZIP 裡的資料夾不需要對應 ZipEntry，只有檔案才有 ZipEntry
        // D. Deflater 和 Inflater 是一般應用中最常直接呼叫的類別
        //
        // 解析：
        // A. 錯誤 — ZipFile 只能處理磁碟上的實體檔案，網路串流尚未落地，無法使用
        // B. 正確 — ZipInputStream 可包裝任何 InputStream，適合邊下載邊處理的場景
        // C. 錯誤 — 目錄也是一個 ZipEntry，其 getName() 以斜線「/」結尾，isDirectory() 依此判斷
        // D. 錯誤 — Deflater/Inflater 是底層壓縮引擎，一般使用更高層的 ZipOutputStream/ZipInputStream

        System.out.println("正確答案：B");
        System.out.println();
        System.out.println("選用原則整理：");
        System.out.println("  手上有磁碟實體 ZIP 檔，要找特定 entry → ZipFile（隨機存取）");
        System.out.println("  資料來自網路串流，邊收邊處理         → ZipInputStream（循序讀取）");
        System.out.println("  要修改已存在的 ZIP 檔（就地更新）      → NIO ZIP File System");
        System.out.println();
        System.out.println("ZipEntry 小提醒：目錄也是一個 ZipEntry，getName() 以 '/' 結尾");
    }
}
