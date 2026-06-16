// Ch20 練習3-2：固定代碼 + 動態訊息
// parseAge() 成功時回傳 200 + 動態訊息，失敗時回傳固定的 ERROR_CODE + 例外訊息

public class ExerciseAdv2_AgeParser {

    static class BaseRes {
        private final int code;
        private final String message;

        BaseRes(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

    public static final int ERROR_CODE = 400;

    static BaseRes parseAge(String input) {
        try {
            int age = Integer.parseInt(input);
            return new BaseRes(200, "解析成功，年齡為：" + age);
        } catch (NumberFormatException e) {
            return new BaseRes(ERROR_CODE, e.getMessage());
        }
    }

    public static void main(String[] args) {
        for (String input : new String[]{"25", "twenty"}) {
            BaseRes res = parseAge(input);
            System.out.println("輸入 \"" + input + "\" -> [" + res.getCode() + "] " + res.getMessage());
        }
    }
}
