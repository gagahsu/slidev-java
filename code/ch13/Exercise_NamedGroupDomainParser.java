// Ch13 練習：具名分組解析網域
// 使用具名分組解析網址，分別取出 protocol 與 host 兩個欄位

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Exercise_NamedGroupDomainParser {

    public static void main(String[] args) {
        // https? 用 ? 表示 s 可有可無，剛好可以同時比對 http 與 https，
        // 比寫成 (?:http|https) 更精簡 —— 這裡甚至不需要非擷取分組 (?:...)
        // (?<protocol>...) 與 (?<host>...) 是具名分組，最後可用 group("名稱") 取值，
        // 比用編號 group(1)、group(2) 更直觀
        // host 部分加上 - ，因為網域名稱常包含連字號（如 my-site.org）
        String regex = "(?<protocol>https?)://(?<host>[\\w.-]+)";

        String[] urls = {
            "https://www.example.com",
            "http://my-site.org",
            "ftp://example.com", // ❌ protocol 不是 http/https，不會 matches
        };

        for (String url : urls) {
            Matcher m = Pattern.compile(regex).matcher(url);
            if (m.matches()) {
                System.out.println(url + " → protocol = " + m.group("protocol")
                        + ", host = " + m.group("host"));
            } else {
                System.out.println(url + " → 不符合格式");
            }
        }
    }
}
