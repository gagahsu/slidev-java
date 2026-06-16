// Ch09 練習：設計 Book 類別的建構子
// 練習建構子多載 + this(...) 鏈式呼叫：三個建構子互相委託，最終都匯流到同一個建構子完成欄位設定

class Book {
    String title;
    String author;
    int price;

    Book(String title, String author, int price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    // 委託給三個參數的建構子，price 預設為 0
    Book(String title, String author) {
        this(title, author, 0);
    }

    // 委託給兩個參數的建構子，title/author 預設為 "未命名"
    Book() {
        this("未命名", "未命名");
    }

    void displayInfo() {
        System.out.println(title + " / " + author + " / " + price);
    }
}

public class Exercise1_BookConstructor {
    public static void main(String[] args) {
        Book b1 = new Book("Java 入門", "小明", 500);
        Book b2 = new Book("Java 進階", "小華");
        Book b3 = new Book();

        b1.displayInfo(); // Java 入門 / 小明 / 500
        b2.displayInfo(); // Java 進階 / 小華 / 0
        b3.displayInfo(); // 未命名 / 未命名 / 0
    }
}
