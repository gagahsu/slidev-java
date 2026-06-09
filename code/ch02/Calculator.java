/**
 * 練習二：寫一段有完整註解的程式
 * 示範 Javadoc 文件註解、單行註解的正確用法
 */
public class Calculator {

    /**
     * 計算兩個整數的和並回傳結果。
     *
     * @param a 第一個整數
     * @param b 第二個整數
     * @return 兩數相加的結果
     */
    public int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        // 建立 Calculator 物件以呼叫 add 方法
        Calculator calc = new Calculator();

        // 呼叫 add 方法計算 3 + 5，並印出結果
        System.out.println(calc.add(3, 5));
    }
}
