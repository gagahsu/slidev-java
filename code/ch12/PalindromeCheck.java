import java.util.Scanner;

/**
 * 練習四：迴文判斷
 * 利用 StringBuilder.reverse() 判斷字串是否為迴文
 */
public class PalindromeCheck {
    public static void main(String[] args) {
        String[] testCases = {"禰豆子豆禰", "鬼滅之刃", "racecar", "hello", "上海自來水來自海上"};

        for (String word : testCases) {
            printResult(word);
        }

        // 互動輸入
        System.out.println("\n請輸入字串（輸入 q 結束）：");
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            if ("q".equalsIgnoreCase(input)) break;
            printResult(input);
        }
        scanner.close();
    }

    static void printResult(String word) {
        System.out.println("「" + word + "」→ " + (checkPalindrome(word) ? "是迴文" : "不是迴文"));
    }

    static boolean checkPalindrome(String word) {
        // 利用 StringBuilder.reverse() 取得反轉字串
        String reversed = new StringBuilder(word).reverse().toString();
        return word.equals(reversed);
    }
}
