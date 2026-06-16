// Ch22 練習：串流基礎概念
// 認證模擬題：Java I/O 串流的基礎概念，答案選 B

public class ExerciseAdv_StreamBasics {

    public static void main(String[] args) {
        // 這題是觀念題，請回答下列何者正確：
        //
        // A. 讀取文字檔時應優先使用 InputStream / OutputStream
        // B. InputStream 的 read() 方法在讀到串流結尾時，會回傳 -1   ← 正確答案
        // C. BufferedInputStream 跟 FileInputStream 不能合併使用
        // D. new OutputStream() 可以建立「什麼都不做」的輸出串流
        //
        // 解析：
        // A. 錯誤 — 文字檔應優先使用 Reader / Writer，InputStream/OutputStream 給二進位資料
        // B. 正確 — read() 回傳 -1 代表 EOF，是迴圈終止的信號
        // C. 錯誤 — BufferedInputStream 正是設計來「包裝」FileInputStream（裝飾者模式）
        // D. 錯誤 — OutputStream 是抽象類別，不能直接 new

        System.out.println("正確答案：B");
        System.out.println();
        System.out.println("實際示範：read() 回傳 -1 代表 EOF");

        // 用 ByteArrayInputStream 模擬「讀到底」的行為（不需要外部檔案）
        byte[] data = "Hi".getBytes();
        try (java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(data)) {
            int b;
            while ((b = bais.read()) != -1) {
                System.out.print((char) b + " ");
            }
            System.out.println();
            // 再讀一次，已到底了
            int eof = bais.read();
            System.out.println("read() 到底時回傳：" + eof); // -1
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
