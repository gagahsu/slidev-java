// 練習二：逐行讀取文字檔（CSV 成績報表）
// 使用 BufferedReader + FileReader 讀取 scores.csv，計算每人平均分數

import java.io.*;

public class ScoreReport {

    public static void main(String[] args) throws IOException {
        String filename = args.length >= 1 ? args[0] : "scores.csv";

        // try-with-resources 自動關閉 BufferedReader
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0];
                double sum = 0;
                for (int i = 1; i < parts.length; i++) {
                    sum += Integer.parseInt(parts[i].trim());
                }
                double avg = sum / (parts.length - 1);  // parts.length - 1 = 分數欄位數
                System.out.printf("%-8s: %.2f%n", name, avg);
            }
        }
    }
}
