// 練習一：Byte 檔案複製
// 使用 Buffered Stream 複製二進位檔案（如圖片）

import java.io.*;

public class FileCopy {

    public static void main(String[] args) throws IOException {
        // 使用命令列參數，或使用預設的測試檔名
        String src  = args.length >= 2 ? args[0] : "photo.jpg";
        String dest = args.length >= 2 ? args[1] : "photo_backup.jpg";

        long total = copyFile(src, dest);
        System.out.println("複製完成：" + src + " → " + dest);
        System.out.println("共複製 " + total + " bytes");
    }

    /**
     * 以 8 KB 緩衝區複製二進位檔案，並回傳複製的 byte 數。
     * try-with-resources 確保兩個串流都被關閉。
     */
    static long copyFile(String src, String dest) throws IOException {
        long total = 0;
        try (BufferedInputStream  bis = new BufferedInputStream(new FileInputStream(src));
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dest))) {
            byte[] buf = new byte[8192];
            int n;
            while ((n = bis.read(buf)) != -1) {
                bos.write(buf, 0, n);   // 只寫實際讀到的 bytes，避免結尾殘影
                total += n;
            }
        }
        return total;
    }
}
