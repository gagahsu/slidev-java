// Ch15 練習：用 Objects.hash() 設計 Book
// 練習使用 Objects.hash() 覆寫 hashCode()，驗證「相同內容 -> 相同雜湊碼」

import java.util.Objects;

public class Exercise2_BookHashCode {

    static class Book {
        String isbn;
        String title;

        Book(String isbn, String title) {
            this.isbn = isbn;
            this.title = title;
        }

        // 業界常用：把要用來判斷相等的欄位丟給 Objects.hash() 即可
        @Override
        public int hashCode() {
            return Objects.hash(isbn, title);
        }
    }

    public static void main(String[] args) {
        Book b1 = new Book("978-1", "Java 入門");
        Book b2 = new Book("978-1", "Java 入門");
        Book b3 = new Book("978-1", "Java 進階");

        System.out.println("b1.hashCode() = " + b1.hashCode());
        System.out.println("b2.hashCode() = " + b2.hashCode());
        System.out.println("b1 與 b2 hashCode 相同嗎？" + (b1.hashCode() == b2.hashCode()));
        // → true：isbn、title 皆相同

        System.out.println("b3.hashCode() = " + b3.hashCode());
        System.out.println("b1 與 b3 hashCode 相同嗎？" + (b1.hashCode() == b3.hashCode()));
        // → false：title 不同
    }
}
