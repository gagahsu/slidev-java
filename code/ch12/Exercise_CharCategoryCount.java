// Ch12 練習：字元分類統計
// 逐個字元檢查字串，統計數字、字母（含中文）、空白字元各出現幾次

public class Exercise_CharCategoryCount {
    public static void main(String[] args) {
        String text = "炭治郎123 是 主角！";

        int digit = 0, letter = 0, space = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            // 注意：Character.isLetter 對中文字也會回傳 true，跟只認英文字母的語言不同
            if (Character.isDigit(c)) digit++;
            else if (Character.isLetter(c)) letter++;
            else if (Character.isWhitespace(c)) space++;
            // 「！」這個全形驚嘆號三個條件都不成立，所以不會被計入任何類別
        }

        System.out.println("字串：" + text);
        System.out.println("數字: " + digit + ", 字母: " + letter + ", 空白: " + space);
    }
}
