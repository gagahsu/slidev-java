// Ch20 練習3：throw 實作
// 密碼長度必須在 5–8 個字元之間
// 長度不符時使用 throw 拋出 StringIndexOutOfBoundsException

public class Exercise3_ThrowPassword {

    private static void pwdCheck(String pwdStr) throws StringIndexOutOfBoundsException {
        if (pwdStr.length() >= 5 && pwdStr.length() <= 8) {
            System.out.println("密碼驗證成功：" + pwdStr);
        } else {
            System.out.println("密碼驗證失敗：" + pwdStr);
            throw new StringIndexOutOfBoundsException("密碼長度不符規定");
        }
    }

    public static void main(String[] args) {
        String[] passwords = {"abc", "hello", "password123", "ok123"};

        for (String pwd : passwords) {
            try {
                pwdCheck(pwd);
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("捕捉到異常：" + e.getMessage());
            }
        }
    }
}
