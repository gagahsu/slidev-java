// Ch12 綜合練習：日記格式化器
// 使用 Text Block 建立多行日記範本，用 lines() 逐行取出並過濾空白行，
// 最後用 StringBuilder 加上編號重新組合輸出

public class Exercise_DiaryFormatter {
    public static void main(String[] args) {
        // 1. 使用 Text Block 建立多行日記範本，中間故意夾雜空白行
        String diary = """
                今天和炭治郎一起去訓練，學會了新的呼吸法。

                禰豆子今天狀態不錯，吃了一整顆竹筒便當。

                明天要前往那田蜘蛛山，大家都做好了準備。
                """;

        // 2. 用 lines() 逐行取出，並過濾掉空白行（取代 Spring StringUtils.hasText()）
        // 3. 用 StringBuilder 搭配計數器，對每個有效行 append「編號 + 內容 + 換行」
        StringBuilder sb = new StringBuilder();
        int index = 1;
        for (String line : diary.lines().toList()) {
            if (hasText(line)) {
                sb.append(index).append(". ").append(line).append("\n");
                index++;
            }
        }

        System.out.print(sb);
    }

    /**
     * 判斷字串是否「不為 null 且有非空白內容」
     * 等同 Spring org.springframework.util.StringUtils.hasText()，
     * 這裡用標準 JDK API 自行實作，避免額外依賴
     */
    static boolean hasText(String str) {
        return str != null && !str.isBlank();
    }
}
