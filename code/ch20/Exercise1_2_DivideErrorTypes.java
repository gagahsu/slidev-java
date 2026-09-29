// Ch20 練習1-2：除法計算機與錯誤分類
// divide() 尚未做任何例外處理，觀察 divide(10, 0) 造成程式中止
// 並列舉語法錯誤、語意錯誤、執行期錯誤各一個範例（見下方註解說明）

public class Exercise1_2_DivideErrorTypes {

    public static String divide(int a, int b) {
    	try {
            return Integer.toString(a / b);
        } catch (ArithmeticException e) {
        	System.out.println("異常：" + e);
            System.out.println("toString：" + e.toString());
            System.out.println("getMessage：" + e.getMessage());
            e.printStackTrace();
            return "執行除法運算時須避開除數為0的";
        }
    	
    	
    }

    public static void main(String[] args) {
        System.out.println("10 / 2 = " + divide(10, 2));
        System.out.println("10 / 0 = " + divide(10, 0)); // 執行期錯誤：ArithmeticException，程式中止
        System.out.println("這行不會被執行到");

        // 語法錯誤範例（無法編譯，僅供說明，請勿取消註解）：
         int x = 5;

        // 語意錯誤範例（能編譯執行，但邏輯錯誤）：
        // int total = a - b;  // 想算總和卻用減法
    }
}
