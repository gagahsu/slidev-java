// Ch22 練習二：逐行讀取文字檔
// 使用 BufferedReader + FileReader 讀取 CSV，計算每位學生的平均成績
// 注意：執行前請確認 scores.csv 存在，格式為「姓名,分數1,分數2,...」
// 範例內容：
//   Alice,85,90,78
//   Bob,70,88,95
//   Carol,92,76,84

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ExerciseAdv2_ReadTextLines {

    public static void main(String[] args) throws IOException {
        Path csvPath = Path.of("scores.csv");

        // 若 scores.csv 不存在，自動建立範例資料
        if (!Files.exists(csvPath)) {
            Files.writeString(csvPath,
                "Alice,85,90,78\nBob,70,88,95\nCarol,92,76,84\n");
            System.out.println("已自動建立 scores.csv");
        }

        // BufferedReader 包裝 FileReader，readLine() 逐行讀取
        // try-with-resources 確保 BufferedReader 自動關閉
        try (BufferedReader br = new BufferedReader(new FileReader(csvPath.toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                double sum = 0;
                // parts[0] 是姓名，分數從索引 1 開始
                for (int i = 1; i < parts.length; i++) {
                    sum += Integer.parseInt(parts[i]);
                }
                // parts.length - 1 是分數欄位的數量（排除姓名欄）
                System.out.printf("%-8s: %.2f%n", name, sum / (parts.length - 1));
            }
        }
    }
}
