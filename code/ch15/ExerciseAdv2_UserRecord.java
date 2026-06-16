// Ch15 練習 2：用 Record 重構 User
// 用 record 重新實作 User，自動取得 equals()/hashCode()/toString()，並驗證 HashSet 行為

import java.util.HashSet;
import java.util.Set;

public class ExerciseAdv2_UserRecord {

    // 一行宣告，equals()、hashCode()、toString() 全部自動產生
    record User(String id, String email) { }

    public static void main(String[] args) {
        User u1 = new User("u001", "alice@mail.com");
        User u2 = new User("u001", "alice@mail.com");

        System.out.println(u1); // User[id=u001, email=alice@mail.com]

        System.out.println("u1.equals(u2) = " + u1.equals(u2)); // true
        System.out.println("hashCode 相同嗎？" + (u1.hashCode() == u2.hashCode())); // true

        Set<User> set = new HashSet<>();
        set.add(u1);
        System.out.println("set.contains(u2) = " + set.contains(u2)); // true

        // 比較：改寫前(手動 equals+hashCode+建構子)約 15 行，
        // 改用 record 後僅 1 行，且不會出現「合約沒對齊」的問題
    }
}
