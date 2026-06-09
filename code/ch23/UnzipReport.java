// 練習二：解壓縮並統計
// 解壓縮 ZIP 並輸出統計報告（含 Zip Slip 安全防護）

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.zip.*;

public class UnzipReport {

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.err.println("用法：java UnzipReport <archive.zip> <輸出目錄>");
            System.exit(1);
        }

        String zipPath = args[0];
        Path outputDir = Path.of(args[1]);
        Files.createDirectories(outputDir);

        long totalSize = 0, compressedSize = 0;
        int fileCount = 0, dirCount = 0;

        try (ZipFile zf = new ZipFile(zipPath)) {
            for (ZipEntry entry : Collections.list(zf.entries())) {
                if (entry.isDirectory()) {
                    dirCount++;
                    Files.createDirectories(outputDir.resolve(entry.getName()));
                    continue;
                }

                fileCount++;
                totalSize      += entry.getSize();
                compressedSize += entry.getCompressedSize();

                // Zip Slip 防護：確認解壓縮路徑不會逃逸到目標目錄之外
                Path targetPath = outputDir.resolve(entry.getName()).normalize();
                if (!targetPath.startsWith(outputDir.normalize())) {
                    System.err.println("Zip Slip 攻擊偵測，略過：" + entry.getName());
                    continue;
                }

                Files.createDirectories(targetPath.getParent());
                try (InputStream in = zf.getInputStream(entry);
                     OutputStream out = new BufferedOutputStream(new FileOutputStream(targetPath.toFile()))) {
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
        System.out.printf("  總壓縮大小：%,d bytes / 壓縮比：%.2f%%%n", compressedSize, ratio);
    }
}
