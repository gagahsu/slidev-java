// Ch13 練習二（綜合）：日誌解析小工具
// 1. 使用具名分組解析日期、時間、日誌等級
// 2. 使用 Pattern.MULTILINE 搭配 Matcher.results() 統計 ERROR 行數
// 3. 使用環視斷言取出 amount=$ 後面的數字（不含 $）

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Exercise_LogParser {

    public static void main(String[] args) {
        parseLogFields();
        countErrorLines();
        extractAmount();
    }

    // 1. 具名分組解析日期、時間、等級
    static void parseLogFields() {
        String regex = "(?<date>\\d{4}-\\d{2}-\\d{2}) (?<time>\\d{2}:\\d{2}:\\d{2}) "
                + "(?<level>[A-Z]+).*";
        String log = "2024-05-20 14:32:01 ERROR user=alice ip=192.168.1.10 amount=$1500";

        Matcher m = Pattern.compile(regex).matcher(log);
        if (m.matches()) {
            System.out.println("date  = " + m.group("date"));  // 2024-05-20
            System.out.println("time  = " + m.group("time"));  // 14:32:01
            System.out.println("level = " + m.group("level")); // ERROR
        }
    }

    // 2. MULTILINE + results()：讓 ^ 和 $ 對每一行都生效，再用 count() 統計符合的行數
    static void countErrorLines() {
        String logs = "2024-05-20 14:32:01 ERROR user=alice\n"
                + "2024-05-20 14:33:10 INFO user=bob\n"
                + "2024-05-20 14:35:42 ERROR user=carol";

        long errorCount = Pattern.compile("^.*ERROR.*$", Pattern.MULTILINE)
                .matcher(logs).results().count();
        System.out.println("ERROR 行數：" + errorCount); // 2
    }

    // 3. 正向後行 (?<=amount=\$)：要求左邊必須是 "amount=$"，
    //    但這段文字不算進比對結果，所以 group() 只會拿到 "1500"，不含 "amount=$"
    static void extractAmount() {
        Matcher m = Pattern.compile("(?<=amount=\\$)\\d+")
                .matcher("amount=$1500");
        if (m.find()) {
            System.out.println("amount = " + m.group()); // 1500
        }
    }
}
