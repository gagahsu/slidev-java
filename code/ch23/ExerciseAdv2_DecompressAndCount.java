// Ch23 練習二：解壓縮並統計
// 使用 ZipFile 解壓縮 ZIP 並輸出統計報告（檔案數、目錄數、壓縮比）
// 用法：java ExerciseAdv2_DecompressAndCount <archive.zip> <輸出目錄>
// 若不傳參數，會先呼叫 ExerciseAdv1_CompressDirectory 準備測試 ZIP

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ExerciseAdv2_DecompressAndCount {

    public static void main(String[] args) throws IOException {
        String zipPath;
        Path outputDir;

        if (args.length >= 2) {
            zipPath = args[0];
            outputDir = Path.of(args[1]);
        } else {
            // 先確保有測試 ZIP 可用
            zipPath = "output.zip";
            outputDir = Path.of("test_output");
            if (!Files.exists(Path.of(zipPath))) {
                prepareSampleZip(zipPath);
                System.out.println("已自動建立測試 ZIP：" + zipPath);
            }
        }

        Files.createDirectories(outputDir);

        long totalSize = 0, compressedSize = 0;
        int fileCount = 0, dirCount = 0;

        // ZipFile 可直接用 getEntry() 隨機存取，並帶有 getSize() / getCompressedSize()
        try (ZipFile zf = new ZipFile(zipPath)) {
            for (ZipEntry e : Collections.list(zf.entries())) {
                if (e.isDirectory()) {
                    dirCount++;
                    Files.createDirectories(outputDir.resolve(e.getName()));
                    continue;
                }
                fileCount++;
                totalSize      += e.getSize();
                compressedSize += e.getCompressedSize();

                // 解壓縮：取得該 entry 的 InputStream 並寫出到目標路徑
                Path target = outputDir.resolve(e.getName());
                Files.createDirectories(target.getParent());

                // Zip Slip 安全防護：確認目標路徑確實在輸出目錄之內
                String canonicalOut = outputDir.toFile().getCanonicalPath();
                String canonicalTarget = target.toFile().getCanonicalPath();
                if (!canonicalTarget.startsWith(canonicalOut + java.io.File.separator)) {
                    throw new IOException("Zip Slip 安全防護：路徑逸出 " + e.getName());
                }

                try (var in = zf.getInputStream(e);
                     OutputStream out = Files.newOutputStream(target)) {
                    in.transferTo(out);
                }
            }
        }

        double ratio = totalSize > 0
            ? (1.0 - (double) compressedSize / totalSize) * 100
            : 0.0;

        System.out.println("解壓縮完成！");
        System.out.printf("  檔案數量：%d / 目錄數量：%d%n", fileCount, dirCount);
        System.out.printf("  總原始大小：%,d bytes%n", totalSize);
        System.out.printf("  總壓縮大小：%,d bytes / 壓縮比：%.2f%%%n",
            compressedSize, ratio);
    }

    /** 建立包含幾個假檔案的最小 ZIP，方便獨立測試 */
    static void prepareSampleZip(String zipPath) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(Path.of(zipPath)))) {
            String[] entries = {"logs/2024/app.log", "readme.txt"};
            for (String name : entries) {
                zos.putNextEntry(new ZipEntry(name));
                zos.write(("content of " + name).getBytes());
                zos.closeEntry();
            }
        }
    }
}
