// Ch22 練習三：目錄瀏覽工具
// 遞迴列出指定目錄下所有的檔案與子目錄，以樹狀結構輸出

import java.io.File;

public class ExerciseAdv3_DirectoryBrowser {

    /**
     * 遞迴印出目錄樹狀結構。
     *
     * @param dir    要列出的目錄
     * @param indent 當前縮排字串（遞迴時每深一層加兩個空格）
     */
    static void printTree(File dir, String indent) {
        File[] files = dir.listFiles();
        if (files == null) return;  // null 檢查：目錄不存在或 I/O 錯誤時 listFiles() 回傳 null

        for (File f : files) {
            if (f.isDirectory()) {
                System.out.println(indent + "[目錄] " + f.getName());
                // 遞迴往下一層，縮排加兩個空格
                printTree(f, indent + "  ");
            } else {
                System.out.printf("%s[檔案] %s (%d bytes)%n",
                    indent, f.getName(), f.length());
            }
        }
    }

    public static void main(String[] args) {
        // 預設瀏覽當前工作目錄；可改為任意絕對路徑，例如 new File("C:\\Temp")
        // 若傳入命令列參數，使用第一個參數作為目標目錄
        String target = args.length > 0 ? args[0] : ".";
        File root = new File(target);

        if (!root.exists() || !root.isDirectory()) {
            System.out.println("指定路徑不是有效目錄：" + target);
            return;
        }

        System.out.println("[目錄] " + root.getName());
        printTree(root, "  ");
    }
}
