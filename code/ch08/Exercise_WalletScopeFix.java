// Ch08 練習：修正 Scope 遮蔽問題
// 原本 setBalance 裡 balance = balance 沒有效果，因為參數 balance 遮蔽了同名的 instance 變數，
// 修正方式：用 this.balance 明確指向物件自己的欄位

class Wallet {
    int balance = 0; // instance 變數

    void setBalance(int balance) {
        // this.balance 是欄位，右邊的 balance 是參數
        // 面試常考：setter 參數與欄位同名時，忘記加 this 會造成「設定了卻沒生效」的隱蔽 bug
        this.balance = balance;
    }
}

public class Exercise_WalletScopeFix {
    public static void main(String[] args) {
        Wallet w = new Wallet();
        w.setBalance(500);
        System.out.println(w.balance); // 500
    }
}
