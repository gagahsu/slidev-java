// Ch23 練習一：壓縮指定目錄
// 遞迴壓縮來源目錄下的所有檔案與子目錄，保留目錄結構
// 用法：java ExerciseAdv1_CompressDirectory <來源目錄> <輸出.zip>
// 若不傳參數，會自動建立一個測試目錄並壓縮

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ExerciseAdv1_CompressDirectory {

    public static void main(String[] args) throws IOException {
        Path srcDir;
        String zipFileName;

        if (args.length >= 2) {
            srcDir = Path.of(args[0]);
            zipFileName = args[1];
        } else {
            // 自動建立測試目錄結構
            srcDir = Path.of("test_src");
            zipFileName = "output.zip";
            setupTestDirectory(srcDir);
            System.out.println("已自動建立測試目錄：" + srcDir);
        }

        if (!Files.isDirectory(srcDir)) {
            System.out.println("來源目錄不存在：" + srcDir);
            return;
        }

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFileName))) {
            Files.walk(srcDir)
                 .filter(p -> !Files.isDirectory(p))
                 .forEach(p -> {
                     // relativize 計算相對路徑作為 entry 名稱
                     // Windows 路徑分隔符 \ 替換為 /（ZIP 規格要求）
                     String entryName = srcDir.relativize(p).toString()
                                              .replace("\\", "/");
                     try {
                         zos.putNextEntry(new ZipEntry(entryName));
                         Files.copy(p, zos);  // 取代手動 buffer 迴圈
                         zos.closeEntry();
                     } catch (IOException e) {
                         throw new RuntimeException("壓縮失敗：" + p, e);
                     }
                 });
        }

        System.out.println("壓縮完成：" + srcDir + " → " + zipFileName);
    }

    /** 建立測試用目錄結構 */
    static void setupTestDirectory(Path base) throws IOException {
        Path subDir = base.resolve("logs/2024");
        Files.createDirectories(subDir);
        Files.writeString(base.resolve("readme.txt"), "這是根目錄的說明檔\n");
        Files.writeString(subDir.resolve("app.log"), "2024-01-01 啟動成功\n");
        Files.writeString(subDir.resolve("error.log"), "（無錯誤）\n");
    }
}
