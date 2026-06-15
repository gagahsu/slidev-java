// Ch23 練習：NIO 增量更新 ZIP
// 使用 NIO ZIP File System 就地修改一個已存在的 ZIP 檔案：
// 新增目錄、複製檔案進去、列出所有 entry

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Exercise_IncrementalZipUpdate {

    public static void main(String[] args) throws IOException {
        // 執行前準備：
        // 1. 若 archive.zip 尚不存在，先用 prepareSampleZip() 建立一個範例 ZIP
        // 2. today.log 是要被加入 ZIP 的外部檔案，這裡也會自動建立一個範例檔
        Path zipPath = Path.of("archive.zip");
        Path logFile = Path.of("today.log");

        if (!Files.exists(zipPath)) {
            prepareSampleZip(zipPath);
        }
        if (!Files.exists(logFile)) {
            Files.writeString(logFile, "2024-01-01 system started\n");
        }

        // 證照常考：開啟「已存在」的 ZIP 用 Map.of()（空設定）；
        // 建立「新」ZIP 才需要 Map.of("create", "true")
        try (FileSystem fs = FileSystems.newFileSystem(zipPath, Map.of())) {

            // ① 在 ZIP 內建立新目錄
            Path logsDir = fs.getPath("logs");
            Files.createDirectories(logsDir);

            // ② 把外部檔案複製進 ZIP
            Path target = fs.getPath("logs/today.log");
            Files.copy(logFile, target);

            // ③ 走訪並印出 ZIP 內所有 entry 路徑
            Path root = fs.getPath("/");
            Files.walk(root).forEach(System.out::println);
        }
        // try 區塊結束、fs.close() 之後，archive.zip 就已經包含新增的內容，
        // 不需要額外的「儲存」步驟
        System.out.println("更新完成：" + zipPath);
    }

    /**
     * 建立一個最小的範例 ZIP，內含一個 readme.txt，方便獨立測試本題。
     */
    static void prepareSampleZip(Path zipPath) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(zipPath))) {
            zos.putNextEntry(new ZipEntry("readme.txt"));
            zos.write("sample archive\n".getBytes());
            zos.closeEntry();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
