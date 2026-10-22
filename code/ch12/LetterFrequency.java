/**
 * 練習三：字母頻率統計
 * 1. 統計 "AABCBDCDACBDA" 中 A、B、C、D 各出現幾次
 * 2. 挑戰：對任意字串統計所有字元出現次數
 */
public class LetterFrequency {
    public static void main(String[] args) {
    	String html = "<div>A</div><div>B</div>";

    	// 貪婪：比對到最後一個 </div>
    	System.out.println(html.replaceAll("<div>.*</div>", "TEXT")); 
    	// TEXT

    	// 懶惰：比對到第一個 </div> 就停止
    	System.out.println(html.replaceAll("<div>.*?</div>", "TEXT")); 
    	// TEXTTEXT
    	
    	
        String str = "AABCBDCDACBDA";

        // 第一部分：固定統計 A B C D
        System.out.println("字串：" + str);
        System.out.println("--- 固定統計 A B C D ---");
        char[] targets = {'A', 'B', 'C', 'D'};
        for (char c : targets) {
            int count = 0;
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == c) count++;
            }
            System.out.println(c + " 出現 " + count + " 次");
        }

        // 第二部分：對任意字串統計所有字元
        // char 的範圍是 0~65535，可以直接當陣列索引，每個字元一格
        System.out.println("\n--- 通用統計（任意字串）---");
        int[] count = new int[65536];
        for (int i = 0; i < str.length(); i++) {
            count[str.charAt(i)]++;
        }
        for (int i = 0; i < count.length; i++) {
            if (count[i] > 0) {
                System.out.println((char) i + " 出現 " + count[i] + " 次");
            }
        }
    }
}
