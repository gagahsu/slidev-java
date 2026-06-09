// 練習一：壓縮指定目錄
// 遞迴壓縮來源目錄下所有檔案，保留目錄結構

import java.io.*;
import java.nio.file.*;
import java.util.zip.*;

public class ZipTool {

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.err.println("用法：java ZipTool <來源目錄> <輸出.zip>");
            System.exit(1);
        }

        Path srcDir = Path.of(args[0]);
        String destZip = args[1];

        if (!Files.isDirectory(srcDir)) {
            System.err.println("來源目錄不存在：" + args[0]);
            System.exit(1);
        }

        zipDirectory(srcDir, destZip);
        System.out.println("壓縮完成：" + srcDir + " → " + destZip);
    }

    /**
     * 將 srcDir 下所有檔案遞迴壓縮到 destZip。
     * ZIP entry 名稱使用相對路徑，Windows 的 \ 替換為 /（ZIP 規格要求）。
     */
    static void zipDirectory(Path srcDir, String destZip) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(
                new BufferedOutputStream(new FileOutputStream(destZip)))) {

            // Files.walk() 取得所有子路徑（含子目錄中的檔案）
            Files.walk(srcDir)
                 .filter(p -> !Files.isDirectory(p))
                 .forEach(p -> {
                     // relativize() 計算相對路徑作為 ZIP entry 名稱
                     String entryName = srcDir.relativize(p).toString()
                                              .replace("\\", "/");
                     try {
                         zos.putNextEntry(new ZipEntry(entryName));
                         Files.copy(p, zos);     // 直接複製檔案內容到 ZIP
                         zos.closeEntry();
                     } catch (IOException e) {
                         throw new UncheckedIOException(e);
                     }
                 });
        }
    }
}
