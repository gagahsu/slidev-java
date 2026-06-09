/**
 * 練習四：迴文判斷
 * 利用 StringBuilder.reverse() 判斷字串是否為迴文
 */
public class PalindromeCheck {
    public static void main(String[] args) {
        String[] testCases = {"禰豆子豆禰", "鬼滅之刃", "racecar", "hello", "上海自來水來自海上"};

        for (String word : testCases) {
            boolean isPalindrome = checkPalindrome(word);
            System.out.println("「" + word + "」→ " + (isPalindrome ? "是迴文" : "不是迴文"));
        }
    }

    static boolean checkPalindrome(String word) {
        // 利用 StringBuilder.reverse() 取得反轉字串
        String reversed = new StringBuilder(word).reverse().toString();
        return word.equals(reversed);
    }
}
