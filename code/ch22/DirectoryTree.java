// 練習三：目錄瀏覽工具
// 遞迴列出目錄樹狀結構，顯示檔案大小

import java.io.File;
import java.util.Arrays;

public class DirectoryTree {

    public static void main(String[] args) {
        // 使用命令列參數，或預設為當前目錄
        String path = args.length >= 1 ? args[0] : ".";
        File root = new File(path);

        if (!root.exists()) {
            System.err.println("路徑不存在：" + path);
            System.exit(1);
        }

        System.out.println("[目錄] " + root.getName());
        printTree(root, "  ");
    }

    /**
     * 遞迴印出目錄樹。
     * @param dir    當前目錄或檔案
     * @param indent 縮排字串（每深一層增加兩個空格）
     */
    static void printTree(File dir, String indent) {
        File[] files = dir.listFiles();
        if (files == null) return;  // null 表示 dir 不存在或無法讀取（權限不足）

        Arrays.sort(files);  // 排序：目錄與檔案依名稱排列

        for (File f : files) {
            if (f.isDirectory()) {
                System.out.println(indent + "[目錄] " + f.getName());
                printTree(f, indent + "  ");  // 遞迴進入子目錄，縮排加深
            } else {
                System.out.printf("%s[檔案] %s (%d bytes)%n",
                        indent, f.getName(), f.length());
            }
        }
    }
}
