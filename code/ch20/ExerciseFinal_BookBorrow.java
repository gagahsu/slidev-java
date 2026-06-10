// Ch20 綜合練習：圖書借閱系統
// 整合自訂例外、throw/throws、try-catch-finally、try-with-resources、enum 錯誤代碼

import java.util.Scanner;

class BookNotAvailableException extends Exception {
    BookNotAvailableException(String message) {
        super(message);
    }
}

public class ExerciseFinal_BookBorrow {

    enum BorrowResult {
        SUCCESS(200, "借閱成功"),
        NOT_FOUND(404, "查無此書"),
        NOT_AVAILABLE(409, "書籍已被借出");

        private final int code;
        private final String message;

        BorrowResult(int code, String message) {
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

    static BorrowResult borrowBook(String[] ids, boolean[] available, String bookId)
            throws BookNotAvailableException {
        for (int i = 0; i < ids.length; i++) {
            if (ids[i].equals(bookId)) {
                if (!available[i]) {
                    throw new BookNotAvailableException(BorrowResult.NOT_AVAILABLE.getMessage());
                }
                available[i] = false;
                return BorrowResult.SUCCESS;
            }
        }
        throw new BookNotAvailableException(BorrowResult.NOT_FOUND.getMessage());
    }

    public static void main(String[] args) {
        String[] bookIds = {"B001", "B002", "B003"};
        boolean[] available = {true, false, true};

        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.print("請輸入書籍編號（輸入 exit 結束）：");
                String bookId = sc.next();
                if (bookId.equalsIgnoreCase("exit")) {
                    break;
                }

                try {
                    BorrowResult result = borrowBook(bookIds, available, bookId);
                    System.out.println("[" + result.getCode() + "] " + result.getMessage());
                } catch (BookNotAvailableException e) {
                    System.out.println(e.getMessage());
                } finally {
                    System.out.println("本次查詢結束");
                }
            }
        }
    }
}
