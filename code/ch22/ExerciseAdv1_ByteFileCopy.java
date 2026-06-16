// Ch22 練習一：Byte 檔案複製
// 使用 BufferedInputStream + BufferedOutputStream 複製二進位檔案
// 注意：執行前請先確認 photo.jpg 存在，或改用其他已存在的檔案路徑

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ExerciseAdv1_ByteFileCopy {

    public static void main(String[] args) throws IOException {
        // 若 photo.jpg 不存在，先建立一個測試用檔案（以文字模擬二進位）
        Path src = Path.of("photo.jpg");
        Path dst = Path.of("photo_backup.jpg");
        if (!Files.exists(src)) {
            Files.writeString(src, "（測試用）這不是真正的圖片，只是讓練習可以跑起來。");
            System.out.println("已自動建立測試用 photo.jpg");
        }

        long total = 0;

        // try-with-resources 一次宣告兩個資源，用分號分隔
        // 關閉順序與宣告順序相反：先關 bos，再關 bis
        try (BufferedInputStream bis = new BufferedInputStream(
                 new FileInputStream(src.toFile()));
             BufferedOutputStream bos = new BufferedOutputStream(
                 new FileOutputStream(dst.toFile()))) {

            byte[] buf = new byte[8192];
            int n;
            // 關鍵：write(buf, 0, n) 而非 write(buf)，只寫實際讀到的 bytes
            while ((n = bis.read(buf)) != -1) {
                bos.write(buf, 0, n);
                total += n;
            }
        }

        System.out.println("複製完成：" + src + " → " + dst);
        System.out.println("共複製 " + total + " bytes");
    }
}
