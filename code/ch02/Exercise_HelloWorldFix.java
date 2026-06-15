// Ch02 練習：找出編譯與執行的錯誤
// 修正後的 HelloWorld：檔名與 class 名稱一致，執行時不加 .class

public class Exercise_HelloWorldFix {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}

// 原題目（阿明的錯誤版本）：
// 1. 檔名存成 helloworld.java，但 public class 名稱是 HelloWorld
//    -> 編譯錯誤：檔名與 public class 名稱必須一致（含大小寫）
// 2. 執行指令打成 java HelloWorld.class
//    -> 執行錯誤：java 指令不需要（也不能）加 .class
//
// 正確操作：
//   javac HelloWorld.java
//   java HelloWorld
