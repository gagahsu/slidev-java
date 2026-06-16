// Ch02 練習二：寫一段有完整註解的程式

/**
 * 簡易加法計算機，示範三種 Java 註解的正確用法。
 * Javadoc 文件註解（/** ... */）加在類別或方法定義的上方，
 * 用來自動產生 API 文件（javadoc Calculator.java）。
 */
public class Exercise2_CommentedProgram {

    /**
     * 計算兩整數之和。
     *
     * @param a 第一個加數
     * @param b 第二個加數
     * @return a 與 b 的總和
     */
    public int add(int a, int b) {
        return a + b;
    }

    public static void main(String[] args) {
        // 建立 Calculator 物件，用來呼叫 add 方法
        Exercise2_CommentedProgram calc = new Exercise2_CommentedProgram();

        // 呼叫 add(3, 5) 並印出結果
        System.out.println(calc.add(3, 5)); // 預期輸出：8

        /* 區塊註解（/* ... */）適合暫時關閉多行程式碼，或說明一段邏輯的整體目的：
           這裡示範加減乘除四則運算，讓讀者快速了解 add 方法的延伸應用。 */
    }
}
