// Ch15 綜合練習：BankAccount 類別
// 用 record 改寫 BankAccount，驗證自動產生的 equals()/hashCode()/toString()，
// 並思考「全欄位比較」在識別碼類別（identity）上的限制

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Exercise_BankAccountRecord {

    // record 版本：equals()/hashCode()/toString() 皆為「全欄位比較」
    record BankAccount(String accountNo, String owner, double balance) { }

    // 一般 class 版本：只用 accountNo 判斷是否為同一帳戶（identity）
    static class BankAccountById {
        String accountNo;
        String owner;
        double balance;

        BankAccountById(String accountNo, String owner, double balance) {
            this.accountNo = accountNo;
            this.owner = owner;
            this.balance = balance;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o instanceof BankAccountById other) {
                return Objects.equals(accountNo, other.accountNo);
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(accountNo); // 與 equals() 一致，只用 accountNo
        }

        @Override
        public String toString() {
            return "BankAccountById{accountNo='%s', owner='%s', balance=%.1f}"
                    .formatted(accountNo, owner, balance);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== record 版本（全欄位比較）===");
        BankAccount a1 = new BankAccount("A001", "Alice", 1000);
        BankAccount a2 = new BankAccount("A001", "Alice", 500); // accountNo 相同，balance 不同

        System.out.println(a1); // BankAccount[accountNo=A001, owner=Alice, balance=1000.0]
        System.out.println("a1.equals(a2) = " + a1.equals(a2)); // false：balance 不同

        Set<BankAccount> recordSet = new HashSet<>();
        recordSet.add(a1);
        System.out.println("recordSet.contains(a2) = " + recordSet.contains(a2)); // false

        System.out.println("\n=== 一般 class 版本（只用 accountNo 判斷）===");
        BankAccountById b1 = new BankAccountById("A001", "Alice", 1000);
        BankAccountById b2 = new BankAccountById("A001", "Alice", 500); // balance 不同

        System.out.println(b1);
        System.out.println("b1.equals(b2) = " + b1.equals(b2)); // true：accountNo 相同即視為同一帳戶

        Set<BankAccountById> idSet = new HashSet<>();
        idSet.add(b1);
        System.out.println("idSet.contains(b2) = " + idSet.contains(b2)); // true

        // 思考解答：
        // record 的 equals() 是「全欄位比較」，無法只比較部分欄位。
        // 若需求是「accountNo 相同即視為同一帳戶」，應改用一般 class，
        // 手動覆寫 equals()/hashCode() 只使用 accountNo —— 這就是「值物件」
        // (record 適用) 與「具識別碼的物件」(一般 class 較適用) 的差異。
    }
}
