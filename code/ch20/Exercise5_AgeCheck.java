// Ch20 練習5：綜合練習 — 年齡投票資格檢查系統
// 滿18歲：輸出「xx歲的年齡歡迎投票」
// 未滿18歲：拋出自訂異常，訊息為「年齡不符規定」，輸出「xx歲的年齡太輕」

// 自訂異常，繼承 StringIndexOutOfBoundsException（依題目要求）
class AgeException extends StringIndexOutOfBoundsException {
    AgeException(String message) {
        super(message);
    }
}

public class Exercise5_AgeCheck {

    static void ageCheck(int age) throws AgeException {
        if (age >= 18) {
            System.out.println(age + " 歲的年齡歡迎投票");
        } else {
            throw new AgeException("年齡不符規定");
        }
    }

    public static void main(String[] args) {
        int[] ages = {12, 19, 67};

        for (int age : ages) {
            try {
                ageCheck(age);
            } catch (AgeException e) {
                System.out.println(age + " 歲的年齡太輕：" + e.getMessage());
            }
        }
    }
}
